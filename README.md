# Coquille
Template android project for informational content application
No need to know android development to create your application using Coquille. Just overwrite logo, application launcher icons and configuration file and you are ready to create your application

## A. Set up your environement

1. Required software
       - Android SDK:
         http://developer.android.com/sdk/index.html
       - Gradle (optional, Coquille has its own wrapper) :
         http://www.gradle.org/downloads



2. Open the Android SDK Manager and install the following elements
       - Tools > Android SDK Platform-tools (rev 21 or above)
       - Tools > Android SDK Tools (rev 24.0.0 or above)
       - Tools > Android SDK Build-tools version 21 or above
       - Tools > Android SDK Build-tools version 21.1.1 or above
       - Android 5.0 > SDK Platform (API 21)
       - Extras > Android Support Repository
       - Extras > Android Support Library rev21 or above
       - Extras > Google Play services(not yet)
       - Extras > Google Repository

## B. Customize your application
1. Customize appearence
      - Go into the app/src/main/res folder then overwrite ic_launcher.png and brand_logo_big.png files for each resolution
      - Go into the app/src/main/res/values folder and change the appplication name in strings.xml file
      - If you want customize colors, copy the lib/src/main/res/values/color.xml file into the app/src/main/values folder and overwrite colors in copied file

1. Customize content data
Coquille has 2 types of data: 
      - local data as simple json files or html files. All local content files are located in app/src/main/assets folder. Only 2 files are mandatory, about_content.json (used for about screen) and side_menu_offlin.json (used for display offline content). All other files depend on your config file.
      - remote data as simple json file or html files. Using remote files, no need to upgrade application to update content.
      
Simple json files have a simple structure, they are composed of a list of title/body pairs. Body can be written using simple html tags. See sample files for the syntax of this json files.
If you need more complexe content, you can use html files but pay attention they will be displayed in an embedd webview.
One you have your content data files, create a json file to describe the side menu.
Each side menu element is described using 4 fields:
      - label, which contains the text displayed in side menu for the item
      - type, with 3 possible values: 0 for section header element, 1 for html content element and 2 for simple json content element
      - remote, true if content file associated to this menu entry is a remote file, false in other case. This field is not required for section header element
      - data, contains the content data file url or location. For local data file, use the following scheme "file:///android_asset/". It can be an html or simple json file following the type field value
See side_menu_content.json sample file for more details.

Finally, go into the app/src/main/res/values folder and change the config.xml file to set the side menu file. For remote side menu file, the sidemnu_location parameter contains the url. For local file, no need to use the "file:///android_asset/" scheme, set directly the json file name located in assets folder. 


## B. Build your application
TO BE CONTINUED
