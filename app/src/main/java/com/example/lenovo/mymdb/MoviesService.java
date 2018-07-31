package com.example.lenovo.mymdb;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {
    @GET("movie/popular?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US&page=1")
    Call<MovieResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/now_playing?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US&page=1")
    Call<MovieResponse> getNowPlayingMovies(@Query("page") int page);

    @GET("movie/top_rated?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US")
    Call<MovieResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/upcoming?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US")
    Call<MovieResponse> getAllUpcomingMovies(@Query("page") int page);

//    @GET("movie/upcoming")
//    Call<MovieResponse> getAllUpcomingMovies(@Query("api_key") String key, @Query("language") String lang, @Query("page") int page);

    @GET("movie/{id}?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US&page=1")
    Call<MovieExtended> getThisMovieDetails(@Path("id") long id);

    @GET("movie/{id}/credits?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<CastResponse> getThisMovieCast(@Path("id") long id);

    @GET("movie/{movie_id}/videos?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TrailerVideoResponse> getThisMovieTrailersVideos(@Path("movie_id") long movieId);

    @GET("movie/{id}/recommendations?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<MovieResponse> getRecommendedMovies(@Path("id") long id);

    @GET("movie/{id}/similar?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<MovieResponse> getSimilarMovies(@Path("id") long movie_id);

    @GET("movie/{id}/reviews?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<ReviewResponse> getMovieReviews(@Path("id") long movie_id);


    @GET("person/{person_id}?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<Person> getThisPersonDetails(@Path("person_id") long id);

    @GET("person/{person_id}/movie_credits?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<PersonMovieCredits> getThisPersonMovies(@Path("person_id") long id);

    @GET("person/{person_id}/tv_credits?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<PersonTvCredits> getThisPersonTvShows(@Path("person_id") long personId);


    //TVSHOWS
    @GET("tv/airing_today?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getAiringTodayTv(@Query("page") int page);

    @GET("tv/on_the_air?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getOnTheAirTv(@Query("page") int page);

    @GET("tv/popular?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getPopularTv(@Query("page") int page);

    @GET("tv/top_rated?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getTopRatedTv(@Query("page") int page);

    @GET("tv/{tv_id}?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShow> getThisTvDetails(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/credits?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<CastResponse> getThisTvCast(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/videos?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TrailerVideoResponse> getThisTvTrailersVideos(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/recommendations?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getRecommendedTv(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/reviews?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<ReviewResponse> getTvReviews(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/similar?api_key=05bbfb6674e330251ed04e9d72c9e79b")
    Call<TvShowResponse> getSimilarTv(@Path("tv_id") long tv_id);

    @GET("search/multi?api_key=05bbfb6674e330251ed04e9d72c9e79b&language=en-US")
    Call<SearchResponse> getSearchResults(@Query("query") String query);


//    @GET("/person/{person_id}/movie_credits?api_key=05bbfb6674e330251ed04e9d72c9e79b")
//    Call<> getPersonMovieCredits(@Path("person_id") long person_id);


}
