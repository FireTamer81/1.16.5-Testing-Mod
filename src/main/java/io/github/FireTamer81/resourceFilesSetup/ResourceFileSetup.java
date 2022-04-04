package io.github.FireTamer81.resourceFilesSetup;

import io.github.FireTamer81.TestModMain;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ResourceFileSetup {

    //Makes a new folder if it doesn't exist, where files can be placed. Plan to use it for things form species models to story missions
    public static File modResourcesFolder = new File(FMLPaths.GAMEDIR.get().toFile(), "testModResources");

    //Adds the sub folders
    public static File speciesModelsFolder = new File(modResourcesFolder, "species_models");
    public static File missionFilesFolder = new File(modResourcesFolder, "mission_files");

    //Adds the pre-made Files into the Folders
    public static File speciesHumanMaleFileDest = new File(speciesModelsFolder, "human_male_model.json");


    public static void addResourceFoldersAndFiles() {
        if (!modResourcesFolder.exists()) {
            modResourcesFolder.mkdir();

            if (!speciesModelsFolder.exists()) {
                speciesModelsFolder.mkdir();

                copyJSONFile(speciesHumanMaleFileDest, "human_male_model.json");
            }

            if (!missionFilesFolder.exists()) { missionFilesFolder.mkdir(); }
        }
    }


    private static void copyJSONFile(File dataFile, String sourceFile) {
        try {
            dataFile.createNewFile();
        } catch(IOException e) {
            TestModMain.LOGGER.error( "Could not create template json", dataFile.getPath(), e );
        }

        try {
            InputStream inputStream = ResourceFileSetup.class.getResourceAsStream("/assets/" + TestModMain.MOD_ID + "/player_models/human/" + sourceFile);
            FileOutputStream outputStream = new FileOutputStream(dataFile);

            TestModMain.LOGGER.debug( "Copying over " + sourceFile + " to " + dataFile.getPath(), dataFile.getPath() );
            System.out.println(inputStream == null ? "null inputStream for " + sourceFile : sourceFile + " inputStream Good");

            IOUtils.copy(inputStream, outputStream);
        } catch(IOException e) {
            TestModMain.LOGGER.error( "Error copying over " + sourceFile + " json config to " + dataFile.getPath(), dataFile.getPath(), e );
        }
    }
}
