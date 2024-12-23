TODO: to set up this template you will have to change the "template" everywhere to your thing name,
  so that is in:
 1. in settings.gradle
 2. folder/directory name
 3. in build.gradle.kts the whole `bukkitPluginYaml` should be updated.
3.1. `main` make sure this-one is up-to-date, since you changed the directory name
 4. In the main class, the title in the companion object
 5. maybe class name (instead of MinecraftPlugin, you could do TemplatePlugin)

Everything then should work still
yuo can build the plugin jar with the little hammer icon next to the run button
this would build it automatically, I think using the gradle settings (so you don't have to mess with artifact or anything)
the jar will be located in build/libs/Template-1.0.jar

You can just go to github and press the button "use as template" and create a repo via that


when you have it you might also look at the build.gradle.kts to see what you may want to enable and disable (for example the shadowJar stuff)
