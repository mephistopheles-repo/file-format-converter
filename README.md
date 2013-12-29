file-format-converter
=====================
-help -? -h        : this help
-src <path>        : atlas file directory. default: jar file directory
-r                 : recursive search
-filename <regexp> : file name pattern. default: .+\.atlas
-output <path>     : output directory. default: jar file directory
-tmpl <name>       : template name in jar_file_directory/templates/

params example:
-src=/path/to/atlas/files/ -filename="some regexp" -output=/path/to/output/ -tmpl=template_name -r
find all files match regexp in /path/to/atlas/files/ and subdirs.
output files to /path/to/output/ with names equals atlas image name. Source subdirs tree ignored

params passed to template is AtlasFile object

AtlasFile {
    String imageName
    List<Record> records
}

Record {
    String name
    int positionX
    int positionY
    int width
    int height
    int offsetX
    int offsetY
    int originalWidth
    int originalHeight
    boolean rotated
}

List is some kind of array, like c++ vector;

template syntax available at http://freemarker.org
