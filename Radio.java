
/**
 * ---LYRIC---
 *  jk ensemble, mon-fri 2-4pm
 *  reels to ragas, tue 7-8
 *  blue of the night, nightly 10-1am
 *
 *
 * ---RNAG---
 *
 *  an taobh tuathail, mon-fri 10pm-12am
 */
import java.io.IOException;



public class Radio {


    void getRnag(){

        Runtime rt = Runtime.getRuntime();
        try{
            Process pr = rt.exec("vlc -vvv --qt-start-minimized http://av.rasset.ie/av/live/radio/rnag.m3u");
        } catch (IOException e){e.printStackTrace();}
    }

    void getLyric(){
        Runtime rt = Runtime.getRuntime();
        try{
            Process pr = rt.exec("vlc -vvv --qt-start-minimized http://av.rasset.ie/av/live/radio/lyric.m3u");
        } catch (IOException e){e.printStackTrace();}
    }

    void endRadio(){
        Runtime rt = Runtime.getRuntime();
        try{
            Process pr = rt.exec("pkill vlc");
        } catch (IOException e){e.printStackTrace();}
    }


    public static void main(String args[]){

    Radio radio = new Radio();
            radio.getLyric();
    }

}
