GameData
========
About
-----
This package facilitates easy saving of game data as JSON, or in any other
format. Enclosed are the following classes:
- `GameData` Use this class to serialize your objects.
- `JsonAdapter` Implement this interface for custom
  serialization/deserialization of classes. Needed if trying to serialize
  subclasses of a superclass.
- `PropertiesWriter` Implement this interface to serialize to other types of
  data formats.
- `PropertiesReader` Implement this interface to deserialize to other types of
  data formats.
- `JsonReader` Implementation of PropertiesReader to read JSON.
- `JsonWriter` Implementation of PropertiesWriter to write JSON.

Dependencies
------------
You need to download
[gson](https://code.google.com/p/google-gson/downloads/list) and include it in
your build path.
