package de.inventivegames.utils.command;

public class Argument {

	private String	name;
	private String	perm;

	private int		pos	= 0;

	public Argument(String name) {
		this.name = name;
	}

	public Argument(String name, int pos) {
		this.name = name;
		this.pos = pos;
	}

	public void setPermission(String perm) {
		this.perm = perm;
	}

	public Argument addArgument(String name) {
		return new Argument(name, this.pos + 1);
	}

	public int getPos() {
		return this.pos;
	}

}
