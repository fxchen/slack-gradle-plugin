/*
 * Copyright (C) 2023 Slack Technologies, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.slack.sgp.intellij

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import java.util.function.Supplier

interface SkateProjectService {
  fun showWhatsNewWindow()
}

class SkateProjectServiceImpl(private val project: Project) : SkateProjectService {

  override fun showWhatsNewWindow() {
    // TODO
    //  Make the file configurable?
    //  Only show when changed
    //  Only show latest changes
    val projectDir = project.guessProjectDir() ?: return
    val changeLogFile = VfsUtil.findRelativeFile(projectDir, "CHANGELOG.md") ?: return
    val changeLogString = VfsUtil.loadText(changeLogFile)
    val toolWindowManager = ToolWindowManager.getInstance(project)
    toolWindowManager.invokeLater {
      val toolWindow =
        toolWindowManager.registerToolWindow("skate-whats-new") {
          stripeTitle = Supplier { "What's New in Slack!" }
          anchor = ToolWindowAnchor.RIGHT
        }
      WhatsNewPanelFactory().createToolWindowContent(toolWindow, changeLogString)
      toolWindow.show()
    }
  }
}
