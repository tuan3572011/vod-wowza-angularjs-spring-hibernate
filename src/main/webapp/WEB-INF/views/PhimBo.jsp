<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<section>
	<div class="container" style="width: 100%">
		<div class="thumbnail thumb-fix">
			<form action="#" method="get">
				<!--  OrderBy -->
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-lg-3">
						<div class="form-group">
							<label>Sap xep</label> <select class="form-control"
								name="orderBy" class="form-control">
								<option>View</option>
								<option></option>
								<option>Comment</option>
								<option selected="selected">All</option>
							</select>
						</div>
					</div>

					<!--  Category -->
					<div class="col-xs-12 col-sm-6 col-lg-3">
						<div class="form-group">
							<label>Category</label> <select class="form-control"
								name="category">
							</select>
						</div>
					</div>
					<!--  Publish Date -->
					<div class="col-xs-12 col-sm-6 col-lg-3">
						<div class="form-group">
							<label>Nam phat hanh</label> <select class="form-control"
								name="searchByYear">
								<option value="2015">2015</option>
								<option value="2014">2014</option>
								<option value="2013">2013</option>
								<option value="2012">2012</option>
								<option value="2011">2011 về trước</option>
								<option selected="selected">All</option>
							</select>
						</div>
					</div>
					<!--  Country -->
					<div class="col-xs-12 col-sm-6 col-lg-3">
						<div class="form-group">
							<label>Country</label> <select class="form-control"
								name="country">
								<option value="VN">VN</option>
								<option selected="selected">All</option>
							</select>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="thumbnail thumb-fix2">
			<div class="row">
				<div class="clg-lg-12">
					<article>
						<div class="row" style="margin-left: 10px">
							<c:forEach items="${movies }" var="movie">
								<div class="col-xs-12 col-sm-6 col-lg-2"
									style="margin-right: 80px">
									<div class="thumbnail fix">
										<div class="caption">
											<header>${movie.engName}<span>${movie.vnName}</span>
											</header>
											<footer>
												<a href="${movie.trailer }" data-toggle="lightbox"
													data-gallery="youtubevideos" class="btn btn-success">
													Trailer</a><a class="btn btn-info"
													data-ng-click="SerieDetail('${movie.id}','${movie.type}')">Chi
													tiết</a>
											</footer>
											<!-- /.modal-content -->
										</div>
										<div>
											<img class="fixImg" alt="..." src="${movie.image}"> <span
												class="badge bad-fix">IMDb: <c:if
													test="${not empty movie.imdb }">
												${movie.imdb } 
												</c:if> <c:if test="${empty movie.imdb }">
												N/A
												</c:if>
											</span> <span class="badge bad-fix bad-fix2">${movie.publishedYear }</span>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<script type="text/javascript">
							$('.fix').hover(function() {
								$(this).find('.caption').slideDown(250); // .fadeIn(250)
							}, function() {
								$(this).find('.caption').slideUp(250); // .fadeOut(205)
							});
						</script>
						<div style="padding-left: 30%;">
							<form>
								<input type="submit" name="home" value="&lt;&lt;"
									class="button rounded" /><input type="submit" name="prev"
									value="&lt;" class="button rounded" /> <input
									class="form-control" type="text" name="page"
									style="width: 50px;" />&nbsp; of <span>11 &nbsp;</span> <input
									class="button rounded" type="submit" name="next" value="&gt;" /><input
									class="button rounded" type="submit" name="home"
									value="&gt;&gt;" />
							</form>
						</div>
					</article>
				</div>
			</div>
		</div>
	</div>
</section>