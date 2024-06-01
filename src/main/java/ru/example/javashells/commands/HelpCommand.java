package ru.example.javashells.commands;

import ru.example.javashells.interfaces.Command;

public class HelpCommand implements Command {

	@Override
	public String getName() {
		return "help";
	}
	
	@Override
	public void execute(String[] args) {
		System.out.print("cd - еремещщение по каталогам  (-) вернутся назад (- N) вернутся на N попок"
				+ "\ncrdir - создание папки"
				+ "\nexit - выход из ос"
				+ "\n");
	}
}
