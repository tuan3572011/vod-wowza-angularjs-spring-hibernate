package vn.edu.hcmuaf.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import vn.edu.hcmuaf.controller.admin.UploadController;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DataUploadUtility {

	public static boolean uploadVideoToWowza(Session session, String videoName,
			InputStream videoInputStream, InputStream keyInputStream) {
		boolean isTransferOk = false;

		try {
			// transfer data
			final String UPLOAD_FILE_TO = "/usr/local/WowzaStreamingEngine/content";
			File localFile = fromStream2File(videoInputStream, "rrr", ".mp4");
			byte[] buffer = new byte[1500000];
			System.out.println("begin transfer data scp " + videoName);
			isTransferOk = transferDataToEc2UsingScp(session, localFile,
					videoName, UPLOAD_FILE_TO, buffer);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return isTransferOk;
	}

	private static File fromStream2File(InputStream in, String PREFIX,
			String SUFFIX) throws IOException {
		final File tempFile = File.createTempFile(PREFIX, SUFFIX);
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return tempFile;
	}

	public static String generateAndReadVideoKeyFromEc2(Session session,
			String videoName) {
		try {
			// upload key
			String fileUpLoadPath = ResourcesFolderUtility
					.getPathFromResourceFolder(UploadController.class,
							"genkeyWowzaCommand.sh");
			File localFile = new File(fileUpLoadPath);
			String pathToSaveInServer = "/home/ec2-user/";
			boolean isTranferGenKeyCommandFileOk = transferDataToEc2UsingScp(
					session, localFile, localFile.getName(),
					pathToSaveInServer, new byte[500]);
			if (isTranferGenKeyCommandFileOk) {
				// if upload video is ok, generate key for this video
				generateKeyFileForVideoInEC2(session, videoName);
				System.out.println("end execute genkey");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				create480pVideoInEC2(session, videoName);
				createKeyFileFor480pVideoInEC2(session, videoName);

				// read key of this video
				ChannelSftp sftpChannel = (ChannelSftp) session
						.openChannel("sftp");
				sftpChannel.connect();
				String keyLocation = "/usr/local/WowzaStreamingEngine/keys/"
						+ videoName + ".key";
				System.out.println(keyLocation);
				InputStream inputStream = sftpChannel.get(keyLocation);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String keyLine = reader.readLine();
				while (keyLine.isEmpty()) {
					keyLine = reader.readLine();
				}
				System.out.println(keyLine);
				String key = null;
				// key is delimited by :
				if (keyLine.split(":").length == 2) {
					key = keyLine.split(":")[1];
				}
				System.out.println("key: " + key);
				if (key != null) {
					return key;
				}
			}

		} catch (JSchException | SftpException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void create480pVideoInEC2(Session session, String videoName)
			throws JSchException, IOException {
		String extension = videoName.split(":")[1];
		String nameWithoutExtension = videoName.split(":")[0];
		String newVideoName = nameWithoutExtension + "_480" + extension;
		Channel shellChannel = session.openChannel("shell");
		StringBuilder command = new StringBuilder();
		command.append("cd /usr/local/WowzaStreamingEngine/content");
		command.append("\n");

		command.append("ffmpeg -i ");
		command.append(videoName);
		command.append(" -vf scale=420:240  -b:v 50k ");
		command.append(newVideoName);
		command.append("\n");

		executeAndCloseChannel(shellChannel, command.toString());
	}

	private static void createKeyFileFor480pVideoInEC2(Session session,
			String videoName) throws JSchException, IOException {
		String extension = videoName.split(":")[1];
		String nameWithoutExtension = videoName.split(":")[0];
		String newVideoName = nameWithoutExtension + "_480" + extension;
		Channel shellChannel = session.openChannel("shell");
		StringBuilder command = new StringBuilder();
		command.append("cd /usr/local/WowzaStreamingEngine/keys");
		command.append("\n");

		command.append("cp");
		command.append(" ");
		command.append(videoName);
		command.append(".key");
		command.append(" ");
		command.append(newVideoName);
		command.append(".key");
		command.append("\n");

		executeAndCloseChannel(shellChannel, command.toString());
	}

	private static void generateKeyFileForVideoInEC2(Session session,
			String videoName) throws JSchException, IOException {
		Channel shellChannel = session.openChannel("shell");
		StringBuilder command = new StringBuilder();
		command.append("cd /home/ec2-user/");
		command.append("\n");
		command.append("chmod 777 genkeyWowzaCommand.sh");
		command.append("\n");
		command.append("./genkeyWowzaCommand.sh " + videoName);
		command.append("\n");

		executeAndCloseChannel(shellChannel, command.toString());
	}

	private static void executeAndCloseChannel(Channel shellChannel,
			String command) throws JSchException, IOException {
		shellChannel.setInputStream(IOUtils.toInputStream(command, "UTF-8"));
		shellChannel.setOutputStream(System.out);
		shellChannel.connect();
		shellChannel.disconnect();
	}

	private static boolean transferDataToEc2UsingScp(Session ss,
			File localFile, String videoName, String pathToSaveInServer,
			byte[] buffer) {
		BufferedOutputStream bufferedOutput = null;
		Channel channel = null;
		try {
			channel = ss.openChannel("exec");
			String command = "scp -t " + pathToSaveInServer + "/" + videoName;
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			channel.connect();
			long fileSize = localFile.length();
			command = "C0644 " + fileSize + " " + videoName;
			command += "\n";
			out.write(command.getBytes());
			out.flush();

			// send a content of lfile
			InputStream inputStream = new FileInputStream(localFile);
			BufferedInputStream bufferedInput = new BufferedInputStream(
					inputStream);
			bufferedOutput = new BufferedOutputStream(out);
			long existSize = fileSize;
			while (true) {
				int length = bufferedInput.read(buffer, 0, buffer.length);
				if (length <= 0) {
					break;
				}
				bufferedOutput.write(buffer, 0, length);
				bufferedOutput.flush();
				existSize -= length;

				System.out.println((int) ((double) (fileSize - existSize)
						/ fileSize * 100)
						+ " %");
				System.out.println(existSize);
			}
			bufferedInput.close();
			// send '\0'
			buffer[0] = 0;
			bufferedOutput.write(buffer, 0, 1);
			bufferedOutput.flush();

		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			if (bufferedOutput != null) {
				try {
					bufferedOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (channel != null) {
				if (channel.isConnected()) {
					channel.disconnect();
				}
			}
		}
		return true;

	}
}
