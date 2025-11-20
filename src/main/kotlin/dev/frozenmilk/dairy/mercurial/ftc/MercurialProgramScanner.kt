package dev.frozenmilk.dairy.mercurial.ftc

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import dev.frozenmilk.dairy.mercurial.continuations.Scheduler
import dev.frozenmilk.sinister.isPublic
import dev.frozenmilk.sinister.isStatic
import dev.frozenmilk.sinister.sdk.opmodes.OpModeScanner
import dev.frozenmilk.sinister.targeting.WideSearch
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import kotlin.reflect.KVisibility
import kotlin.reflect.jvm.kotlinProperty

@Suppress("UNUSED")
object MercurialProgramScanner : OpModeScanner() {
	override val targets = WideSearch()

	class MercurialProgramConverter(private val program: Mercurial.Program) : LinearOpMode() {
		override fun runOpMode() {
			val scheduler = Scheduler.Standard()
			val context = Context(
				{
					if (isStarted) if (isStopRequested) State.STOP
					else State.LOOP
					else State.INIT
				},
				scheduler,
				hardwareMap,
				telemetry,
				gamepad1,
				gamepad2,
			)
			program.exec(context)
		}
	}

	private fun isProgram(cls: Class<*>) =
		Mercurial.RegisterableProgram::class.java.isAssignableFrom(cls)

	override fun scan(
		loader: ClassLoader,
		cls: Class<*>,
		registrationHelper: RegistrationHelper,
	) {
		cls.declaredFields.filter { field ->
			field.type == Mercurial.ProgramRegistrar::class.java
					&& field.isStatic()
		}.forEach { field ->
			field.isAccessible = true
			val registrar = field.get(null) as Mercurial.ProgramRegistrar
			registrar.register { registerableProgram ->
				registrationHelper.register(
					OpModeMeta.Builder()
						.setName(requireNotNull(registerableProgram.name) { "dynamic registration of a program must provide a name" })
						.setGroup(registerableProgram.group ?: OpModeMeta.DefaultGroup)
						.setFlavor(registerableProgram.type)
						.setSource(OpModeMeta.Source.ANDROID_STUDIO)
						.build()
				) { MercurialProgramConverter(registerableProgram.program) }
			}
		}

		cls.declaredFields.filter { field ->
			field.type == Mercurial.RegisterableProgram::class.java
					&& field.kotlinProperty?.let { it.visibility == KVisibility.PUBLIC } ?: field.isPublic()
					&& field.isStatic()
		}.forEach {
			it.isAccessible = true
			val registerableProgram = it.get(null) as Mercurial.RegisterableProgram
			registrationHelper.register(
				OpModeMeta.Builder()
					.setName(registerableProgram.name ?: it.name)
					.setGroup(registerableProgram.group ?: OpModeMeta.DefaultGroup)
					.setFlavor(registerableProgram.type)
					.setSource(OpModeMeta.Source.ANDROID_STUDIO)
					.build()
			) { MercurialProgramConverter(registerableProgram.program) }
		}
	}
}