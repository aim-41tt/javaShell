package ru.example.javashells.commands.catalogues;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class CopyCommand implements Command {

	private DirectoryManager directoryManager;

	/**
	 * @param directoryManager
	 */
	public CopyCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "copy";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: copy <file/dir> <directory>");
			return;
		}
		
		 Path currentDirectory = Paths.get(directoryManager.getCurrentDirectory().getAbsolutePath());
		    Path sourceFile = currentDirectory.resolve(args[1]);
		    Path targetDir = Paths.get(args[2]);

		    Runnable copyAction = () -> {
		        try {
		            if (!Files.exists(targetDir)) {
		                Files.createDirectories(targetDir);
		            }

		            Path targetPath = targetDir.resolve(sourceFile.getFileName());
		            Files.copy(sourceFile, targetPath, StandardCopyOption.REPLACE_EXISTING);

		            String resultMessage = Files.isDirectory(targetPath) ?
		                    "Каталог " + sourceFile.getFileName() + " успешно скопирован в " + targetPath :
		                    "Файл " + sourceFile.getFileName() + " успешно скопирован в " + targetPath;
		            
		            System.out.println(resultMessage);
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    };

		    copyAction.run();
	}

}
