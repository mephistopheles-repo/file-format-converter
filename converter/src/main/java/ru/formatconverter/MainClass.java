package ru.formatconverter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
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

    public static void main(String[] args) throws Exception {
        Map<String, String> params = Utils.getPrams(args);

        if (params.containsKey("help") || params.containsKey("?") || params.containsKey("h")) {
            Utils.printHelp();
            return;
        }

        List<File> files = getFileList(params.get("src"), params.get("filename"), params.containsKey("r"));
        if (files.size() <= 0){
            System.out.println("Atlas files not found. Exit.");
            return;
        }
        System.out.println("found " + files.size() + " files");

        List<AtlasFile> records = parseSourceFiles(files);
        int count = 0;
        for (AtlasFile atlas : records) {
            count += atlas.getCount();
        }
        System.out.println("parsed " + count + " records");

        if (params.containsKey("tmpl")) {
            String templateName = params.get("tmpl");
            if (!templateName.endsWith(".ftl")) {
                templateName += ".ftl";
            }

            String outputDir = Utils.getCurrentDirectory().getAbsolutePath();
            if (params.containsKey("output")) {
                outputDir = params.get("output");
            }

            templateAndWrite(records, templateName, outputDir);
        } else {
            System.out.println("invalid parameter: 'tmpl' key cant be null");
        }
    }

    private static void templateAndWrite(List<AtlasFile> atlasFiles, String templateName, String outputDir) throws IOException {
        Configuration cfg = Utils.initFreemarkerConfig();
        Template template = cfg.getTemplate(templateName);
        File directory = new File(outputDir);

        if (!directory.isDirectory()) {
            throw new IOException("directory '" + directory.getAbsolutePath() + "' not found");
        }

        for (AtlasFile atlas : atlasFiles) {
            File outputFile;
            if (directory.isFile()) {
                outputFile = directory;
            } else {
                if (!directory.exists()){
                    directory.mkdir();
                }
                outputFile = new File(outputDir + File.separator + atlas.getImageName()+".atlas");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                 template.process(atlas,bw);
            } catch (TemplateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static List<AtlasFile> parseSourceFiles(List<File> files) {
        List<AtlasFile> records = new ArrayList<>();

        for (File f : files) {
            if (f.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    AtlasFile atlas = new AtlasFile();
                    String buff = br.readLine();
                    atlas.setImageName(Utils.parseAtlasNameFromString(buff));
                    while ((buff = br.readLine()) != null) {
                        Record record = Utils.parseRecordFromString(buff);
                        atlas.getRecords().add(record);
                    }
                    records.add(atlas);
                } catch (FileNotFoundException e) {
                    //never happens
                } catch (IOException e) {
                    //todo add log
                }
            }
        }

        return records;
    }

    private static List<File> getFileList(String src, String fileName, boolean recursive) {
        List<File> files = new ArrayList<>();
        File directory;
        if (src == null) {
            directory = Utils.getCurrentDirectory();
        } else {
            directory = new File(src);
        }

        if (directory.isFile()) {
            files.add(directory);
        } else if (directory.exists()) {
            if (fileName == null) {
                fileName = ".+\\.atlas";
            }
            Pattern pattern = Pattern.compile(fileName);
            files = getFiles(directory, pattern, recursive);
        } else {
            //todo add log directory not found
        }

        return files;
    }

    public static List<File> getFiles(File directory, final Pattern filename, final boolean recursive) {
        List<File> files = new ArrayList<>();

        File[] innerFiles = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                boolean isMatch;
                if (file.isDirectory() && recursive){
                    isMatch = true;
                } else {
                    Matcher matcher = filename.matcher(name);
                    isMatch = matcher.matches();
                }

                return isMatch;
            }
        });
        for (File f : innerFiles) {
            if (f.isDirectory() && recursive) {
                files.addAll(getFiles(f, filename, recursive));
            } else if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }
}
