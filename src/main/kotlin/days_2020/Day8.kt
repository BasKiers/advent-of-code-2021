package days_2020

import util.Day

class Day8 : Day(8, 2020) {

    enum class Command {
        ACC,
        JMP,
        NOP
    }

    data class Instruction(val command: Command, val value: Int) {
        companion object {
            fun from(command: String): Instruction {
                val cmd = Command.valueOf(command.substring(0..2).uppercase())
                val value = command.substring(4).toInt()

                return Instruction(cmd, value)
            }
        }
    }

    class Program(val instructions: List<Instruction>) : Iterator<Program> {
        var cursor = 0;
        var accumulator = 0;
        var visited = mutableListOf<Int>()

        companion object {
            fun from(program: List<String>): Program {
                val instructions = program.map(Instruction::from)
                return Program(instructions)
            }
        }

        fun exec(instr: Instruction) {
            visited.add(cursor)
            when (instr.command) {
                Command.ACC -> {
                    accumulator += instr.value
                    cursor++
                }
                Command.JMP -> cursor += instr.value
                Command.NOP -> cursor++
            }
        }

        fun hasTerminated() = cursor == instructions.size

        override fun hasNext() = cursor < instructions.size && !visited.contains(cursor)

        override fun next(): Program {
            exec(instructions[cursor])
            return this
        }
    }

    override fun partOne(): Any {
        val program = Program.from(inputList)
        program.forEach { _ -> }
        return program.accumulator
    }

    override fun partTwo(): Any {
        val instructions = inputList.map(Instruction::from);
        val alternativePrograms = instructions.mapIndexed { index, instruction ->
            when (instruction.command) {
                Command.NOP -> instructions.toMutableList()
                    .apply { this[index] = Instruction(Command.JMP, instruction.value) }
                Command.JMP -> instructions.toMutableList()
                    .apply { this[index] = Instruction(Command.NOP, instruction.value) }
                else -> null
            }
        }.filterNotNull().map { Program(it) }
        for (program in alternativePrograms) {
            program.forEach { _ -> }
            if (program.hasTerminated()) {
                return program.accumulator
            }
        }
        throw AssertionError("Expected at least 1 program to finish")
    }

}