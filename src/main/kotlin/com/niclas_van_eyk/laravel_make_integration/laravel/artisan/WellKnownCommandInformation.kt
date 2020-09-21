package com.niclas_van_eyk.laravel_make_integration.laravel.artisan

class WellKnownCommandInformation {
    var wellKnownOptionTypes: MutableMap<String, MutableMap<String, OptionType>> = HashMap()

    init {
        addOptionType("migrate:status", "--help", OptionType.Flag)
    }

    fun addOptionType(command: String, option: String, type: OptionType) {
        val optionTypesForCommand = wellKnownOptionTypes.getOrPut(command, { HashMap() })
        optionTypesForCommand[option] = type
    }

    fun updateOptionTypes(commands: List<Command>) {
        for (command in commands) {
            val typesForCommandOptions = wellKnownOptionTypes[command.name] ?: continue

            for (option in command.options) {
                if (typesForCommandOptions.containsKey(option.name)) {
                    option.type = typesForCommandOptions[option.name]!!
                } else if (typesForCommandOptions.containsKey(option.shortForm)) {
                    option.type = typesForCommandOptions[option.shortForm]!!
                }
            }
        }
    }
}
