package ru.example.javashells.commands;

import ru.example.javashells.interfaces.Command;

public class ClsCommand implements Command {

	@Override
	public String getName() {
		return "cls";
	}

	@Override
	public void execute(String[] args) {
		try {

			String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				// Для Windows
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				// Для Unix/Linux/MacOS
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
