package com.niclas_van_eyk.laravel_make_integration.laravel.artisan

import com.intellij.openapi.project.Project
import com.niclas_van_eyk.laravel_make_integration.laravel.LaravelProject

class ProjectCommands (val laravelProject: LaravelProject, val project: Project) {
    var commands = mutableListOf<Command>()
    var makeCommandNames = emptyList<String>()

    fun readCommandsFromProject() {
        val result = laravelProject.artisan.execute(project, "")

        if (result.wasFailure) {
            // TODO
            print(result.log)
        }

        makeCommandNames = parseArtisanMakeCommandNames(result.log)
    }

    fun readCommandMetadata() {
        for (commandName in makeCommandNames) {
            val parts = commandName.split(":")
            val result = laravelProject.artisan.execute(project, parts[0], parts[1], listOf("--help"))

            if (result.wasFailure) {
                continue
            }

            commands.add(parseArtisanCommandFromHelp(result.logWithoutNewLines.trim(), commandName))
        }
    }
}