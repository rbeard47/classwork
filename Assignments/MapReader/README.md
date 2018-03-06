# Map Reader
Create a class that reads a map description file for later use in a tiled map game.
## Goals
* Create the main Program class and MapReader class
* Create test classes used to unit test the code
## Description
The map format describes a complete tiled map using Json format.  This format include the type of map, version, height, width and tileset used to draw the map.

```json
"map": {
  "version": "1.0",
  "tiledversion": "2018.03.05",
  "orientation": "orthogonal",
  "renderorder": "right-down",
  "width": "25",
  "height": "25",
  "tilewidth": "32",
  "tileheight": "32",
  "infinite": "0",
  "tileset":[
    { "firstgrid": "1", "source": "test.tsx"},
    { "firstgrid": "49","source": "test.tsx"}
  ],
  "layers": [
    {"name": "DungeonMap",
      "width": "25",
      "height": "25",
      "locked": "1",
      "data":
      "1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,9,20,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,21,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,40,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,32,30,30,30,30,30,30,30,30,30,30,30,30,40,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,25,26,27,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,33,36,43,30,25,27,30,30,30,9,11,9,11,30,30,30,30,30,32,30,30,30,30,30,30,33,35,47,30,33,35,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,33,44,26,26,45,35,30,30,30,9,11,9,11,30,30,30,30,32,30,30,30,30,30,30,30,41,42,42,42,42,43,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,11,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,9,11,9,28,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,29,11,17,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,19"
      }
  ]
}
```

## Map File Format
The map file is in JSON (JavaScript Object Notation) format (https://en.wikipedia.org/wiki/JSON "JSON Wikipedia Page").  This is common format in use today and worthwhile to learn and understand, especially for web development or other code that uses Javascript.
The file basically gives all of the required information needed to build simple or complex maps such as the overall map width and height and links to the actual tileset files that contain the sprite sheets.  The "data" member of the "layers" object is an index into the spritesheet contained in the tileset file.  So from the data, "1,2,2,2,2,2,..." indicates that you want tile #1, then tile #2 multiple times followed by additional tile numbers.  The format allows you to build a map from different spritesheets, each containing some of the tiles needed to draw the map and other map layers.
## Design Goal
Using a JSON library, read the complete file into a set of classes that represent a map.  Keep in mind that a future version will need to also read the tileset file (test.tsx) in the above document so you might want to consider a separate Tileset class to later handle reading the tileset file.
A good JSON parser to use is called Gson (https://github.com/google/gson/blob/master/UserGuide.md "Google JSON").
You will need to get the Gson jar file from the Maven repository (https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/gson-2.8.2.jar).

Copy this file to location like c:\netbeans\lib\gson and add a Library in NetBeans (Tools -> Libraries).  Click New Library... and give it a name like Gson.  Next click Add JAR/Folder... and select the gson-2.8.2.jar file).

When you create your initial project, right-click the Project name in NetBeans and add this library to the project.  In the Properties for the project on the left-hand side of the Project Properties UI is node called Libraries.  If you select Libraries, you can add the Gson library to the project.

See this link for sample code that reads a Json file using the JsonReader object. (https://stackoverflow.com/questions/16377754/parse-json-file-using-gson).  Also, let me know if you need more information about this as it might be kind of confusing at first.
