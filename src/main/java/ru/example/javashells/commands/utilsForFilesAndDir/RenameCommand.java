package ru.example.javashells.commands.utilsForFilesAndDir;

import java.io.File;

import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

public class RenameCommand implements Command {

	private DirectoryManager directoryManager;

	public RenameCommand(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

	@Override
	public String getName() {
		return "rename";
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 2) {
			System.out.println("rename <oldName file/dir> <newName file/dir>");
			return;
		}

		String oldNameFile = args[1];
		String newNameFile = args[2];
		
		

		String currentDir = directoryManager.getCurrentDirectory().getAbsolutePath();

		File oldFile = new File(currentDir, oldNameFile);
		File newFile = new File(currentDir, newNameFile);

		if (!oldFile.exists()) {
			System.out.println("файла или директории не найдено."+oldFile.getAbsolutePath()+" "+newFile.getAbsolutePath());
			return;
		}

		if (oldFile.renameTo(newFile)) {
			System.out.println("file/dir переименнован в " + newNameFile);
		} else {
			System.out.println("ошибка file/dir не переименнован "+oldFile.getAbsolutePath()+" "+newFile.getAbsolutePath());
		}

	}

}
