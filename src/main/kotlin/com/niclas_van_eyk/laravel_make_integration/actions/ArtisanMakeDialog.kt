package com.niclas_van_eyk.laravel_make_integration.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.ui.EditorTextField
import com.niclas_van_eyk.laravel_make_integration.laravel.LaravelProject
import com.niclas_van_eyk.laravel_make_integration.laravel.artisan.CommandAutocompleteTextField
import com.niclas_van_eyk.laravel_make_integration.laravel.artisan.ProjectCommands
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ArtisanMakeDialog(val project: Project, val laravelProject: LaravelProject, val command: SubCommand): DialogWrapper(project) {
    private lateinit var editor: EditorTextField

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel(BorderLayout(0, 4))
        panel.add(JLabel(command.capitalized), BorderLayout.NORTH)

        val commands = ProjectCommands(laravelProject, project)
        commands.readCommandsFromProject()
        commands.readCommandMetadata()

        val matchingCommand = commands.commands.firstOrNull { it.name.equals(command.asArtisanCommand) }
        val options = matchingCommand?.options?.toMutableList() ?: mutableListOf()

        editor = CommandAutocompleteTextField(project, options, "Test")
        editor.setPreferredWidth(250)
        panel.add(LabeledComponent.create(editor, "Foo Bar"), BorderLayout.SOUTH)
        return panel
    }
}