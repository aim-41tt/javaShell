package ru.example.javashells.components.managers;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class DirectoryManager {

	private File currentDirectory;
	
	public DirectoryManager(File initialDirectory) {
		this.currentDirectory = initialDirectory;
	}

	/**
	 * @return the currentDirectory
	 */
	public File getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * @param currentDirectory the currentDirectory to set
	 */
	public void setCurrentDirectory(File currentDirectory) {
		this.currentDirectory = currentDirectory;
	}
	
	
	
}
