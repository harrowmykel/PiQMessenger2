package ng.com.coursecode.piqmessenger.File;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by User on 11/29/2016.
 */


public class CFile{

    public static final String VID_DIR = CFile.Dir+"Yoka Videos/";
    public static final String PROF_IMG = CFile.IMG_DIR+"Profile Images/";
    public static final String IMG_DIR = CFile.Dir+"Yoka Images/";
    public static final String MSC_DIR = CFile.Dir+"Yoka Music/";
    public static final String Dir="/Yoka/";
    public static final String SD_DIR=Environment.getExternalStorageDirectory().toString();
    public static Toast tstt;
    public static Context ctxt;
    public static FileOutputStream fos;
    public static String sddir=Environment.getExternalStorageDirectory().toString();



    CFile(Context cctx){
        ctxt=cctx;
    }

    public static void setUp(){
        mkHomeDir();
        makeNewImgFile("dlmdkm.jpg");
        makeNewUserProfFile("lmdkdknk");
        makeNewMscFile("dndn");
        makeNewVidFile("dkndk");
        mkDirIfNotExists(VID_DIR);
        mkDirIfNotExists(MSC_DIR);
        mkDirIfNotExists(IMG_DIR);
        mkDirIfNotExists(PROF_IMG);
    }

    public static void mkHomeDir(){
        String f_pth=SD_DIR + CFile.Dir;
        File wallpp=new File(f_pth);
        wallpp.mkdirs();
    }

    public static void mkNewDir(String fnamee, String dir_nm){
        String gvnpth=getDirCnstnt(fnamee);
        String f_pth=SD_DIR + gvnpth+dir_nm;
        File wallpp=new File(f_pth);
        boolean wllp_bool=false;
        if (!wallpp.exists()){
            wllp_bool =wallpp.mkdirs();
        }
    }

    public static Uri mkNewFile(String diro, String fnamee){
        String gvnpth=getDirCnstnt(diro);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
        boolean wllp_bool =wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        wllp_bool=imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee));
    }

    public static Uri getTempUri(String diro, Context c){
        File file=new File(SD_DIR +diro+"jhenkrh.jpg");
        String gvnpth=getDirCnstnt(diro);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try{
            mkDirIfNotExists(gvnpth);
            file=File.createTempFile("YokaTemp", ".jpg", wallpp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    public static Uri getTempUri(String diro){
        File file=new File(diro);
        String gvnpth=getDirCnstnt(diro);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try{
            mkDirIfNotExists(gvnpth);
            file=File.createTempFile("YokaTemp", ".jpg", wallpp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    public static Uri makeNewImgFile(String fnamee){
        String f_pth=SD_DIR+IMG_DIR;
        File wallpp=new File(f_pth);
        wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        //WHY DID I DELETE THIS?
        imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee));
    }

    public static Uri makeNewMscFile(String fnamee){
        String f_pth=SD_DIR+MSC_DIR;
        File wallpp=new File(f_pth);
        wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        //WHY DID I DELETE THIS?
        imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee));
    }

    public static Uri makeNewVidFile(String fnamee){
        String f_pth=SD_DIR+VID_DIR;
        File wallpp=new File(f_pth);
        wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        //WHY DID I DELETE THIS?
        imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee));
    }

    public static Uri makeNewUserProfFile(String fnamee){
        String f_pth=SD_DIR+PROF_IMG;
        File wallpp=new File(f_pth);
        wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        //WHY DID I DELETE THIS?
        imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee));
    }

    public static String getFilePath(String diro, String fnamee){
        String gvnpth=getDirCnstnt(diro);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
        boolean wllp_bool =wallpp.mkdirs();
        String fpth=f_pth+fnamee;
        File imm=new File(fpth);
        wllp_bool=imm.delete();
        return Uri.fromFile(new File(f_pth, fnamee)).toString();
    }

    public static boolean mkDirIfNotExists(String fnamee){
        String gvnpth=getDirCnstnt(fnamee);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
        boolean wllp_bool=false;
        if (wallpp.exists())
        {wllp_bool = true;}
        else {
            if(wallpp.mkdirs()){
                wllp_bool=true;
            }
        }
        return wllp_bool;
    }

    public static boolean isSdMounted(){
        String sddi= Environment.getExternalStorageState();
        return sddi.contentEquals(Environment.MEDIA_MOUNTED);
    }

    public static Uri getDirUri(String diro, String string){
        String gvnpth=getDirCnstnt(diro);
        String  fll_uri=sddir + gvnpth +string;
        return Uri.parse(fll_uri);
    }

    public static void writeFile(String diro, String fnamee, String cntnt){
        String gvnpth=getDirCnstnt(diro);
        String f_pth=SD_DIR + gvnpth;
        File wallpp=new File(f_pth);
        boolean wllp_bool =wallpp.mkdirs();
        if (mkDirIfNotExists(wallpp.toString())){
            File wallppp=new File(f_pth, fnamee);
            try {
                fos=new FileOutputStream(wallppp);
                fos.write(cntnt.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDirCnstnt(String joy){
        return (joy==null)?CFile.Dir:joy;
    }

    public static void dump(String joy, String fnnnme){
        if (joy.contentEquals("img")){
            fnnnme = CFile.IMG_DIR;
        }
        if (joy.contentEquals("msc")){
            fnnnme = CFile.MSC_DIR;
        }
        if (joy.contentEquals("vid")){
            fnnnme = CFile.VID_DIR;
        }
        if (joy.contentEquals("img_usr")){
            fnnnme = CFile.PROF_IMG;
        }
        if (joy.contentEquals("hme")){
            fnnnme = CFile.Dir;
        }
    }

    public static  Uri getOutputMediaFileUri(){
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "kl.jpg"));
    }

    public static String getNewFileDir() {
        return getFilePath(CFile.IMG_DIR, "new_out.jpg");
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}
