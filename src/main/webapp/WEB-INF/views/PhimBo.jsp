<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<section>
    <div class="container" style="width: 100%">
        <div class="thumbnail thumb-fix">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-lg-3">
                    <div class="form-group">
                        <label><spring:message
                                code="film.OrderBy" /></label> <select
                            ng-change="sortFilm()"
                            ng-init="sort = sorts[0]"
                            ng-options="sort as sort.name for sort in sorts"
                            class="form-control" ng-model="sort">
                        </select>
                    </div>
                </div>

                <!--  Category -->
                <div class="col-xs-12 col-sm-6 col-lg-3">
                    <div class="form-group">
                        <label><spring:message
                                code="film.Category" /></label> <select
                            ng-change="categoryFilm()"
                            ng-init="category = categoriesS[0]"
                            ng-options="category as category.name for category in categoriesS"
                            class="form-control" ng-model="category">
                        </select>
                    </div>
                </div>
                <!--  Publish Date -->
                <div class="col-xs-12 col-sm-6 col-lg-3">
                    <div class="form-group">
                        <label><spring:message
                                code="film.PublishDate" /></label> <select
                            ng-change="publishFilm()"
                            autofocus="autofocus"
                            ng-init="publishYear = publishYears[1]"
                            ng-options="publishYear as publishYear.name for publishYear in publishYears"
                            class="form-control" ng-model="publishYear">
                        </select>
                    </div>
                </div>
                <!--  Country -->
                <div class="col-xs-12 col-sm-6 col-lg-3">
                    <div class="form-group">
                        <label><spring:message
                                code="film.Country" /></label> <select
                            ng-change="countryFilm()"
                            ng-init="country = countriesS[0]"
                            ng-options="country as country.name for country in countriesS"
                            class="form-control" ng-model="country">
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="thumbnail thumb-fix2">
            <div class="row">
                <div class="clg-lg-12">
                    <article>
                        <div class="row" style="margin-left: 10px">
                            <div class="col-xs-12 col-sm-6 col-lg-2"
                                style="margin-right: 80px"
                                ng-repeat="movie in movies">
                                <div class="thumbnail fix">
                                    <div class="caption">
                                        <header>
                                            {{movie.engName}}<span>{{movie.vnName}}</span>
                                        </header>
                                        <footer>
                                            <a href="{{movie.trailer }}"
                                                data-toggle="lightbox"
                                                data-gallery="youtubevideos"
                                                class="btn btn-success">
                                                Trailer</a><a
                                                class="btn btn-info"
                                                data-ng-click="SerieDetail(movie.id,movie.type)">Chi
                                                tiết</a>
                                        </footer>
                                        <!-- /.modal-content -->
                                    </div>
                                    <div>
                                        <img class="fixImg" alt="..."
                                            src="{{movie.image}}">
                                        <span class="badge bad-fix">IMDb:
                                            <span
                                            ng-if="movie.imdb != null">{{movie.imdb
                                                }}</span> <span
                                            ng-if="movie.imdb == null">N/A</span>
                                        </span> <span
                                            class="badge bad-fix bad-fix2">{{movie.publishedYear
                                            }}</span>
                                    </div>
                                </div>
                                <script type="text/javascript">
																																	$(
																																			'.fix')
																																			.hover(
																																					function() {
																																						$(
																																								this)
																																								.find(
																																										'.caption')
																																								.slideDown(
																																										250); // .fadeIn(250)
																																					},
																																					function() {
																																						$(
																																								this)
																																								.find(
																																										'.caption')
																																								.slideUp(
																																										250); // .fadeOut(205)
																																					});
																																</script>
                            </div>
                        </div>

                        <div style="padding-left: 30%;">
                            <form>
                                <input type="submit" name="home"
                                    value="&lt;&lt;"
                                    class="button rounded" /><input
                                    type="submit" name="prev"
                                    value="&lt;" class="button rounded" />
                                <input class="form-control" type="text"
                                    name="page" style="width: 50px;" />&nbsp;
                                of <span>11 &nbsp;</span> <input
                                    class="button rounded" type="submit"
                                    name="next" value="&gt;" /><input
                                    class="button rounded" type="submit"
                                    name="home" value="&gt;&gt;" />
                            </form>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </div>
</section>