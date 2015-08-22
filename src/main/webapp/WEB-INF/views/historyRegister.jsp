<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<table style="width: 70%;">
		<thead>
			<tr>
				<th>STT</th>
				<th>Tên Phim</th>
				<th>Ngày Đăng Ký</th>
				<th>Thời Gian Còn Lại</th>
			</tr>
		</thead>
		<tbody ng-repeat="regis in registers">
			<tr>
				<td>stt</td>
				<td></td>
				<td>regis.registerDate</td>
				<td>regis.noDateExpires</td>
			</tr>
		</tbody>
	</table>
</div>