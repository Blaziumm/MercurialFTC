package dev.frozenmilk.dairy.mercurial.ftc

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta

object Mercurial {
	class RegisterableProgram(
		val name: String?,
		val group: String?,
		val type: OpModeMeta.Flavor,
		val program: Program,
	)

	fun interface Program {
		fun exec(context: Context)
	}

	object UntypedProgramBuilder {
		fun withType(type: OpModeMeta.Flavor) = ProgramBuilder(type)
	}

	class ProgramBuilder(
		val type: OpModeMeta.Flavor,
		val name: String? = null,
		val group: String? = null,
	) {
		fun withName(name: String) = ProgramBuilder(
			type,
			name,
			group,
		)

		fun withGroup(group: String) = ProgramBuilder(
			type,
			name,
			group,
		)

		fun withProgram(program: Program) = RegisterableProgram(
			name,
			group,
			type,
			program,
		)
	}

	@JvmStatic
	fun buildProgram() = UntypedProgramBuilder

	//
	// TeleOp
	//

	@JvmStatic
	fun teleop(program: Program) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// program
		.withProgram(program)

	@JvmStatic
	fun teleop(
		name: String,
		program: Program,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// name
		.withName(name)
		// program
		.withProgram(program)

	@JvmStatic
	fun teleop(
		name: String,
		group: String,
		program: Program,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// name
		.withName(name)
		// group
		.withName(group)
		// program
		.withProgram(program)

	@JvmSynthetic
	fun teleop(program: Context.() -> Unit) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// program
		.withProgram(program)

	@JvmSynthetic
	fun teleop(
		name: String,
		program: Context.() -> Unit,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// name
		.withName(name)
		// program
		.withProgram(program)


	@JvmSynthetic
	fun teleop(
		name: String,
		group: String,
		program: Context.() -> Unit,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.TELEOP)
		// name
		.withName(name)
		// group
		.withName(group)
		// program
		.withProgram(program)

	//
	// Autonomous
	//

	@JvmStatic
	fun autonomous(program: Program) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// program
		.withProgram(program)

	@JvmStatic
	fun autonomous(
		name: String,
		program: Program,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// name
		.withName(name)
		// program
		.withProgram(program)

	@JvmStatic
	fun autonomous(
		name: String,
		group: String,
		program: Program,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// name
		.withName(name)
		// group
		.withName(group)
		// program
		.withProgram(program)

	@JvmSynthetic
	fun autonomous(program: Context.() -> Unit) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// program
		.withProgram(program)

	@JvmSynthetic
	fun autonomous(
		name: String,
		program: Context.() -> Unit,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// name
		.withName(name)
		// program
		.withProgram(program)


	@JvmSynthetic
	fun autonomous(
		name: String,
		group: String,
		program: Context.() -> Unit,
	) = buildProgram()
		// type
		.withType(OpModeMeta.Flavor.AUTONOMOUS)
		// name
		.withName(name)
		// group
		.withName(group)
		// program
		.withProgram(program)

	fun interface ProgramRegistrationHelper {
		fun register(program: RegisterableProgram)
	}

	fun interface ProgramRegistrar {
		fun register(helper: ProgramRegistrationHelper)
	}
}
