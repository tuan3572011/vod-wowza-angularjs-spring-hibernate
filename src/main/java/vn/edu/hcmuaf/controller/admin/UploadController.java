package vn.edu.hcmuaf.controller.admin;

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
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.hcmuaf.util.ResourcesFolderUtility;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

@Controller
@RequestMapping("/UploadController")
public class UploadController {
	private String video_upload_secret_key = "";

	@RequestMapping("/Image/Layout")
	public String uploadImageLayout() {
		return "UploadImage";
	}

	private String randomName() {
		StringBuilder bd = new StringBuilder();
		Random rd = new Random();
		for (int i = 0; i < 5; i++) {
			bd.append(rd.nextInt(10));
		}
		bd.append(System.currentTimeMillis());
		return bd.toString();
	}

	@RequestMapping(value = "/Image/Save", method = RequestMethod.POST)
	public String doUploadImage(@RequestParam("image") MultipartFile multipart,
			Map<String, Object> map) throws IOException {
		System.out.println("save image");
		InputStream inputStream = null;
		if (!multipart.isEmpty()) {
			try {
				inputStream = multipart.getInputStream();
				String originName = multipart.getOriginalFilename();
				long length = multipart.getSize();
				String imageType = originName
						.substring(originName.length() - 3);

				String contentType = "";
				if (imageType.equals("png")) {
					contentType = "image/png";
				} else if (imageType.equals("jpg")) {
					contentType = "image/jpeg";
				} else if (imageType.equals("gif")) {
					contentType = "image/gif";
				} else if (imageType.equals("tif")) {
					contentType = "image/tiff";
				} else if (imageType.equals("bmp")) {
					contentType = "image/bmp";
				} else {
					map.put("message", "Wrong type");
					return "upload";
				}
				// neu kieu anh ko dung voi anh goc
				String name = this.randomName() + "." + imageType;

				String folder = "Images";
				boolean isOK = UploadController.upS3(inputStream, folder, name,
						length, contentType);
				if (isOK) {
					map.put("message",
							"https://s3-ap-southeast-1.amazonaws.com/vod-wowza/"
									+ folder + "/" + name);
				} else {
					map.put("message", "FAIL");
				}
			} catch (Exception e) {
				map.put("message", e.getMessage());
			}
		}

		return "UploadImage";
	}

	@RequestMapping("/Trailer/Layout")
	public String uploadTrailerLayout() {
		return "UploadTrailer";
	}

	@RequestMapping(value = "/Trailer/Save", method = RequestMethod.POST)
	public String doUploadTrailer(
			@RequestParam("trailer") MultipartFile multipart,
			Map<String, Object> map) throws IOException {
		InputStream inputStream = null;
		if (!multipart.isEmpty()) {
			try {
				inputStream = multipart.getInputStream();
				String originName = multipart.getOriginalFilename();
				long length = multipart.getSize();
				String trailerType = originName
						.substring(originName.length() - 3);
				String contentType = "application/octet-stream";
				String name = this.randomName() + "." + trailerType;
				String folder = "Trailer";
				System.out.println("S3 Problem");
				boolean isOK = UploadController.upS3(inputStream, folder, name,
						length, contentType);
				if (isOK) {
					map.put("message",
							"https://s3-ap-southeast-1.amazonaws.com/vod-wowza/"
									+ folder + "/" + name);
				} else {
					map.put("message", "FAIL");
				}
			} catch (Exception e) {
				map.put("message", e.getMessage());
			}
		}

		return "UploadTrailer";
	}

	@RequestMapping("/Film/Layout")
	public String uploadFilmLayout() {
		return "UploadFilm";
	}

	@RequestMapping(value = "/Film/Save", method = RequestMethod.POST)
	public String doUploadFilm(
			@RequestParam("film") MultipartFile multipartFilm,
			Map<String, Object> map) throws IOException {
		InputStream videoInputStream = null;
		InputStream keyInputStream = null;
		if (!multipartFilm.isEmpty()) {
			try {
				// keyInputStream = multipartKey.getInputStream();
				videoInputStream = multipartFilm.getInputStream();
				String hostAndUser = "ec2-user@52.74.51.146";
				// validate video name
				String videoName = this.randomName() + ".mp4";
				System.out.println("begin upto wowza");
				boolean isOK = this.upWowza(hostAndUser, videoInputStream,
						videoName, keyInputStream);
				System.out.println("end upto wowza");
				if (isOK) {
					map.put("message", videoName);
					map.put("key", video_upload_secret_key);
				} else {
					map.put("message", "");
				}
			} catch (Exception e) {
				map.put("message", e.getMessage());
			}
		}

		return "UploadFilm";
	}

	private boolean upWowza(String hostAndUser, InputStream videoInputStream,
			String videoName, InputStream keyInputStream) {

		// File file = new
		// File(ResourcesFolderUtility.getPathFromResourceFolder(
		// UploadController.class, "vod1.pem"));
		// FileWriter writer;
		// try {
		// writer = new FileWriter(file, false);
		// writer.write("abc");
		// writer.flush();
		// writer.close();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// get key path
		String pathToKey = ResourcesFolderUtility.getPathFromResourceFolder(
				UploadController.class, "vod1.pem");
		System.out.println(pathToKey);
		// open jsch session
		Session jschSession = null;
		try {
			jschSession = this.getJschSession(pathToKey, hostAndUser);
			jschSession.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// upload video using this session
		System.out.println(jschSession.getHost() + jschSession.getUserName());
		this.uploadVideo(jschSession, videoName, videoInputStream,
				keyInputStream);
		System.out.println("end upload video");
		// generate key and read key from server
		video_upload_secret_key = this.generateAndReadVideoKey(jschSession,
				videoName);
		// close session
		jschSession.disconnect();
		if (video_upload_secret_key.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean upS3(InputStream inputStream, String folder,
			String fileName, long fileLength, String contentType)

	throws IOException {
		boolean result = false;
		String existingBucketName = "vod-wowza";
		String keyName = folder + "/" + fileName;

		AmazonS3 s3Client = new AmazonS3Client(new PropertiesCredentials(
				UploadController.class
						.getResourceAsStream("AwsCredentials.properties")));

		// s3Client.setEndpoint("autoscaling.ap-southeast-1.amazonaws.com");
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(fileLength);
		PutObjectRequest putObjectRequest = new PutObjectRequest(
				existingBucketName, keyName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult result1 = s3Client.putObject(putObjectRequest);
		inputStream.close();
		result = true;
		System.out.println("Etag:" + result1.getETag() + "-->" + result);
		return result;
	}

	private boolean uploadVideo(Session session, String videoName,
			InputStream videoInputStream, InputStream keyInputStream) {
		boolean isTransferOk = false;

		// Save key file vod1.pem to resources folder
		// try {
		// BufferedReader identityReader = new BufferedReader(
		// new InputStreamReader(keyInputStream));
		// StringBuilder identity = new StringBuilder();
		// String identityLine;
		//
		// identityLine = identityReader.readLine();
		// File file = new File(getPathFromResourceFolder(
		// UploadController.class, "vod1.pem"));
		// if (!file.exists()) {
		// file.createNewFile();
		// }
		// PrintWriter writeKey = new PrintWriter(file);
		// while (identityLine != null) {
		// writeKey.println(identityLine);
		// identity.append(identityLine);
		// identity.append("\n");
		// identityLine = identityReader.readLine();
		// }
		// writeKey.flush();
		// writeKey.close();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		try {
			// transfer data
			final String UPLOAD_FILE_TO = "/usr/local/WowzaStreamingEngine/content";
			File localFile = fromStream2File(videoInputStream, "rrr", ".mp4");
			byte[] buffer = new byte[1500000];
			System.out.println("begin transfer data scp " + videoName);
			isTransferOk = this.transferDataUsingScp(session, localFile,
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

	private String generateAndReadVideoKey(Session session, String videoName) {
		try {
			// upload key
			String fileUpLoadPath = ResourcesFolderUtility
					.getPathFromResourceFolder(UploadController.class,
							"genkeyWowzaCommand.sh");
			File localFile = new File(fileUpLoadPath);
			String pathToSaveInServer = "/home/ec2-user/";
			boolean isTranferGenKeyCommandOk = this.transferDataUsingScp(
					session, localFile, localFile.getName(),
					pathToSaveInServer, new byte[500]);
			if (isTranferGenKeyCommandOk) {
				// if upload video is ok, generate key for this video
				Channel shellChannel = session.openChannel("shell");
				StringBuilder command = new StringBuilder();
				command.append("cd /home/ec2-user/");
				command.append("\n");
				command.append("chmod 777 genkeyWowzaCommand.sh");
				command.append("\n");
				command.append("./genkeyWowzaCommand.sh " + videoName);
				command.append("\n");

				shellChannel.setInputStream(IOUtils.toInputStream(
						command.toString(), "UTF-8"));
				shellChannel.setOutputStream(System.out);
				shellChannel.connect();
				System.out.println("end execute genkey");

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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

	private Session getJschSession(String pathToKey, String hostAndUser)
			throws JSchException {
		String[] hostAndUserArr = hostAndUser.split("@");
		if (hostAndUserArr.length != 2)
			return null;
		String user = hostAndUserArr[0];
		String host = hostAndUserArr[1];
		int port = 22;

		JSch jsch = new JSch();
		Session session = null;
		jsch.addIdentity(pathToKey);
		session = jsch.getSession(user, host, port);
		session.setUserInfo(new UserInfo() {
			@Override
			public void showMessage(String arg0) {
			}

			@Override
			public boolean promptYesNo(String arg0) {
				return true;
			}

			@Override
			public boolean promptPassword(String arg0) {
				return false;
			}

			@Override
			public boolean promptPassphrase(String arg0) {
				return false;
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public String getPassphrase() {
				return null;
			}
		});
		return session;
	}

	private boolean transferDataUsingScp(Session ss, File localFile,
			String videoName, String pathToSaveInServer, byte[] buffer) {
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
