package ru.formatconverter;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created with IntelliJ IDEA.
 * Date: 28.12.13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {

    public static void main(String[] args) {
        Map<String, String> params = getPrams(args);
        List<File> files = getFiles(params.get("src"), params.containsKey("r"));
        for (File f : files) {
            System.out.println(f);
        }
    }

    private static List<File> getFiles(String src, boolean recursive) {
        List<File> files = new ArrayList<>();
        File directory = null;
        if (src == null) {
            directory = getCurrentDirectory();
        } else {
            src = Utils.escapeRegExp(src);
            directory = new File(src);
        }

        if (directory.isFile()) {
            files.add(directory);
        } else {
            files = getFiles(directory);
        }

        return files;
    }

    public static List<File> getFiles(File directory) {
        List<File> files = new ArrayList<>();

        File last = directory;
        File current = directory;

        while (!current.exists()) {
            last = current;
            String temp = current.getParent();
            if (temp == null) {
                temp = File.separator;
            }
            current = new File(temp);
        }

        String lastPath = last.toString();
        String regexp = StringUtils.substringAfterLast(lastPath, File.separator);
        regexp = Utils.unEscapeRegExp(regexp);
        System.out.println(regexp);
        try {
            final Pattern pattern = Pattern.compile(regexp);
            File[] innerFiles = current.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String name = file.getName();
                    Matcher matcher = pattern.matcher(name);
                    boolean isMatch = matcher.matches();
                    return isMatch;
                }
            });
            for (File f : innerFiles) {
                if (f.isDirectory()) {
                    String temp = directory.getAbsolutePath();
                    File innerDirectory = new File(temp.replace(regexp, f.getName()));
                    files.addAll(getFiles(innerDirectory));
                } else if (f.isFile()) {
                    files.add(f);
                }
            }
        } catch (PatternSyntaxException e) {
            //todo add log
            System.out.println(e.getMessage());
        }
        //}

        return files;
    }

    public static File getDirectory() {
        return null;
    }

    public static File getCurrentDirectory() {
        URL url = MainClass.class.getProtectionDomain().getCodeSource().getLocation();
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
}
