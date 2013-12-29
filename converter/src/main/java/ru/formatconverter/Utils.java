package ru.formatconverter;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 29.12.13
 * Time: 1:53
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static File getCurrentDirectory() {
        URL url = Utils.class.getProtectionDomain().getCodeSource().getLocation();
        File path = new File(url.getFile());
        if (path.isFile()) {
            path = new File(path.getParent());
        }

        return path;
    }

    public static Map<String, String> getPrams(String[] args) {
        Map<String, String> pairs = new HashMap<>(args.length, 1.1f);

        for (String s : args) {
            if (s.startsWith("-")) {
                s = s.replaceFirst("-", "");
            }
            if (s.contains("=")) {
                String[] kv = s.split("=");
                if (!pairs.containsKey(kv[0])) {
                    pairs.put(kv[0], kv[1]);
                } else {
                    //todo add log duplicate keys
                }
            } else {
                pairs.put(s, "");
            }
        }

        return pairs;
    }

    public static File getTemplateDirectory() {
        File directory = getCurrentDirectory();
        directory = new File(directory + File.separator + "templates");
        return directory;
    }

    public static Record parseRecordFromString(String input) {
        Record record = new Record();
        String[] arr = input.split("\t");
        record.setName(arr[0]);
        record.setPositionX(Integer.parseInt(arr[1]));
        record.setPositionY(Integer.parseInt(arr[2]));
        record.setWidth(Integer.parseInt(arr[3]));
        record.setHeight(Integer.parseInt(arr[4]));
        record.setOffsetX(Integer.parseInt(arr[5]));
        record.setOffsetY(Integer.parseInt(arr[6]));
        record.setOriginalWidth(Integer.parseInt(arr[7]));
        record.setOriginalHeight(Integer.parseInt(arr[8]));
        record.setRotated(arr.length == 10);

        return record;
    }

    public static void printHelp() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream("/help.txt")))) {
            String buffer;
            while ((buffer = br.readLine())!=null){
                System.out.println(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String parseAtlasNameFromString(String input) {
        String[] arr = input.split(" ");
        return arr[1];
    }

    public static Configuration initFreemarkerConfig() throws IOException {
        Configuration cfg = new Configuration();

        cfg.setDirectoryForTemplateLoading(getTemplateDirectory());

        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }
}
