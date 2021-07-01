package com.google;


import java.util.*;

public class VideoPlayer {

  String currentTitle = "";
  String currentId = "";
  String currentTags = "";
  Boolean pausable = false;
  boolean existingPlaylist = false;
  boolean existingVid = false;
  ArrayList<VideoPlaylist> playLists = new ArrayList<>();
  Video current;
  VideoPlaylist playlist;

  private final VideoLibrary videoLibrary;
  private VideoComparator comparator = new VideoComparator();
  private PlaylistComparator comparator2 = new PlaylistComparator();

  public class VideoComparator implements Comparator<Video> {
    public int compare(Video obj1, Video obj2) {
      return obj1.getTitle().compareTo(obj2.getTitle());
    }
  }

  public class PlaylistComparator implements Comparator<VideoPlaylist> {
    public int compare(VideoPlaylist obj1, VideoPlaylist obj2) {
      return obj1.getPlaylistName().compareTo(obj2.getPlaylistName());
    }
  }

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos: " );
    List<Video> videos = videoLibrary.getVideos();
    Collections.sort(videos, comparator);
    for (Video obj: videos) {
      currentTitle = obj.getTitle();
      currentId = obj.getVideoId();
      currentTags = String.valueOf(obj.getTags());
      System.out.println(currentTitle + " (" + currentId + ") " + currentTags.replace(",", ""));
    }
  }

  public void playVideo(String videoId) {
    List<Video> videos = videoLibrary.getVideos();
    for (Video obj: videos) {
        if (videoId.equals(obj.getVideoId())) {
          if (!currentTitle.equals("")) {
            System.out.println("Stopping video: " + currentTitle);
          }
          System.out.println("Playing video: " + obj.getTitle());
          currentTitle = obj.getTitle();
          currentId = obj.getVideoId();
          currentTags = String.valueOf(obj.getTags());
          existingVid = true;
          pausable = true;
          break;
        }
    }
    if (!existingVid){
        System.out.println("Cannot play video: Video does not exist");
      }
  }

  public void stopVideo() {
    if (!currentTitle.equals("")) {
      System.out.println("Stopping video: " + currentTitle);
      pausable = false;
      currentTitle = "";
      currentId = "";
      currentTags = "";
    }
    else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    Random generator = new Random();
    List<Video> videos = videoLibrary.getVideos();
    int randomIndex = generator.nextInt(videos.size());
    Video randomVid = videos.get(randomIndex);
    if (!currentTitle.equals("")) {
      System.out.println("Stopping video: " + currentTitle);
    }
    System.out.println("Playing video: " + randomVid.getTitle());
    currentTitle = randomVid.getTitle();
    currentId = randomVid.getVideoId();
    currentTags = String.valueOf(randomVid.getTags());
    pausable = true;
  }

  public void pauseVideo() {
    if (pausable && !currentTitle.equals("")) {
      System.out.println("Pausing video: " + currentTitle);
      pausable = false;
    }
    else if (!pausable && !currentTitle.equals("")) {
      System.out.println("Video already paused: " + currentTitle);
    }
    else {
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if (pausable && !currentTitle.equals("")) {
      System.out.println("Cannot continue video: Video is not paused");
    }
    else if (!pausable && !currentTitle.equals("")) {
      System.out.println("Continuing video: " + currentTitle);
      pausable = true;
    }
    else {
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }

  public void showPlaying() {
    if (pausable && !currentTitle.equals("")) {
      System.out.println("Currently playing: " + currentTitle + " (" + currentId + ") " + currentTags.replace(",", ""));
    }
    else if (!pausable && !currentTitle.equals("")) {
      System.out.println("Currently playing: " + currentTitle + " (" + currentId + ") " + currentTags.replace(",", "") + " - PAUSED");
    }
    else {
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    for (VideoPlaylist obj:playLists) {
      if (obj.getPlaylistName().toLowerCase().equals(playlistName.toLowerCase())) {
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        existingPlaylist = true;
        break;
      }
    } if (!existingPlaylist) {
      System.out.println("Successfully created new playlist: " + playlistName);
      playlist = new VideoPlaylist(playlistName, new ArrayList<>());
      playLists.add(playlist);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    existingPlaylist = false;
    existingVid = false;
    List<Video> videos = videoLibrary.getVideos();
    for (VideoPlaylist obj: playLists) {
      String pl = obj.getPlaylistName();
      if (pl.toLowerCase().equals(playlistName.toLowerCase())){
        playlist.setPlaylistName(playlistName);
        existingPlaylist = true;
        break;
      }
    }
    for (Video obj: videos) {
      if (obj.getVideoId().equals(videoId)){
        currentTitle = obj.getTitle();
        currentId = obj.getVideoId();
        currentTags = String.valueOf(obj.getTags());
        current = obj;
        existingVid = true;
      }
    }
    if (existingPlaylist) {
      for (Video obj : playlist.getVideos()) {
        if (videoId.equals(obj.getVideoId())) {
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        }
      } if (existingVid) {
          playlist.addVid(current);
          System.out.println("Added video to " + playlistName + ": " + current.getTitle());
        }
    }
    else {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }
    if (!existingVid) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
    }
  }


  public void showAllPlaylists() {
    Collections.sort(playLists, comparator2);
    if (playLists.equals("")) {
      System.out.println("No playlists exist yet");
    }
    else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist obj: playLists) {
        System.out.println(obj.getPlaylistName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    for (VideoPlaylist obj: playLists) {
      String pl = obj.getPlaylistName();
      if (pl.toLowerCase().equals(playlistName.toLowerCase())){
        playlist.setPlaylistName(playlistName);
        existingPlaylist = true;
      }
    }
  if (existingPlaylist) {
    System.out.println();
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    for (VideoPlaylist obj: playLists) {
      if (obj.getPlaylistName().equals(playlistName)){
        playlist.setPlaylistName(playlistName);
        existingPlaylist = true;
      }
    }
    if (existingPlaylist) {
      playlist.setVideos(new ArrayList<> ());
      System.out.println("Successfully removed all videos from "+ playlistName);

    }
    else {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}