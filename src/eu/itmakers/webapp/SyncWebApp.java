package eu.itmakers.webapp;

import eu.itmakers.Main;
import eu.itmakers.Settings;
import eu.itmakers.Song;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SyncWebApp extends Task<Void>
{
    public static final String listSongsPath = "src/eu/itmakers/songs_list.json";
    public static ServerSocket serverSocket;

    public static ServerSocket getServerSocket()
    {
        return serverSocket;
    }

    public static void generateSongsListJSON(List<Song> songList)
    {
        try
        {
            File file = new File(listSongsPath);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            String outputFileString = "{\n\t\"songs\": [\n";

            Song song;
            for (int i = 0; i < songList.size(); i++)
            {
                song = songList.get(i);
                if (i == songList.size()-1)
                {
                    outputFileString += "\t\t{\n" +
                            "\t\t\t\"id:\" " + i + ",\n" +
                            "\t\t\t\"title\": \""+ song.getTitle() +"\",\n" +
                            "\t\t\t\"artist\": \""+ song.getArtist() +"\",\n" +
                            "\t\t\t\"album\": \""+ song.getAlbum() +"\"\n" +
                            "\t\t}\n";
                }
                else
                {
                    outputFileString += "\t\t{\n" +
                            "\t\t\t\"id:\" " + i + ",\n" +
                            "\t\t\t\"title\": \""+ song.getTitle() +"\",\n" +
                            "\t\t\t\"artist\": \""+ song.getArtist() +"\",\n" +
                            "\t\t\t\"album\": \""+ song.getAlbum() +"\"\n" +
                            "\t\t},\n";
                }
            }
            outputFileString += "\t]\n}";

            bw.write(outputFileString);
            bw.close();
            fileWriter.close();
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("JSON");
    }

    public static boolean run = true;

    @Override
    protected Void call() throws Exception
    {
        while (run)
        {
            try
            {
                serverSocket = new ServerSocket(8080);
                Socket socketAccept = serverSocket.accept();
                InputStreamReader inputStreamReader = new InputStreamReader(socketAccept.getInputStream());
                BufferedReader br = new BufferedReader(inputStreamReader);
                String command = br.readLine();

                PrintWriter out = new PrintWriter(socketAccept.getOutputStream(), false);
                String outMsg = "";
                Song song;

                if (command.equals("PLAY_SONG")) {
                    if (Main.getController().mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                    {
                        Main.getController().mediaPlayer.pause();
                        song = Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(Main.getController().mediaPlayer.getMedia())));
                    }
                    else
                    {
                        Main.getController().mediaPlayer.play();
                        song = Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(Main.getController().mediaPlayer.getMedia())));
                    }
                    outMsg = song.getTitle() + ";" +song.getArtist();
                }
                else if (command.equals("NEXT_SONG"))
                {
                    Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(Main.getController().mediaPlayer.getMedia()))).getSongView().setStyle(null);
                    song = (Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(Main.getController().mediaPlayer.getMedia()))  != Settings.getSongs().size()-1) ? Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(Main.getController().mediaPlayer.getMedia()))+1) : Settings.getSongs().get(0);
                    Platform.runLater(() -> Main.getController().playNewSong(song));
                    outMsg = song.getTitle() + ";" +song.getArtist();
                }
                else if (command.equals("PREV_SONG"))
                {
                    Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia( Main.getController().mediaPlayer.getMedia()))).getSongView().setStyle(null);
                    song = (Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia( Main.getController().mediaPlayer.getMedia()))  != 0) ? Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia( Main.getController().mediaPlayer.getMedia()))-1) : Settings.getSongs().get(Settings.getSongs().size()-1);
                    Platform.runLater(() -> Main.getController().playNewSong(song));
                    outMsg = song.getTitle() + ";" +song.getArtist();
                }
                else if (command.startsWith("SONG_NUM"))
                {
                    try
                    {
                        String[] commands = command.split(" ");
                        song = Settings.getSongs().get(Integer.parseInt(commands[1]));
                        Platform.runLater(() -> Main.getController().playNewSong(song));
                        outMsg = "true";
                    } catch (Exception e) { outMsg = "false";}
                }

                out.write(outMsg + "\r\n");

                out.close();
                br.close();
                socketAccept.close();
                serverSocket.close();
            }
            catch (IOException e)
            {
                run = false;
            }
        }
        return null;
    }
}