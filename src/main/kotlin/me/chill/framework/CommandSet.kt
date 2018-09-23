package me.chill.framework

class CommandSet(val categoryName: String) {
	val commands = mutableListOf<Command>()
	private var isGlobal = false

	inline fun command(name: String, create: Command.() -> Unit) {
		val command = Command(name)
		command.create()
		commands.add(command)
	}

	fun hasCommand(command: String) = commands.stream().anyMatch { it.name == command }

	fun getCommandNames() = commands.map { it.name }.toTypedArray().sortedArray()

	fun setGlobal(isGlobal: Boolean = true) {
		this.isGlobal = isGlobal
	}

	fun getGlobal() = isGlobal
}