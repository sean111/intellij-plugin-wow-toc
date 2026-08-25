package com.github.sean111.wowtoc.action;

import com.github.sean111.wowtoc.icon.TocIcon;
import com.github.sean111.wowtoc.WowTocBundle;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

public class TocNewFileAction extends CreateFileFromTemplateAction implements DumbAware {

    public TocNewFileAction() {
        super("TOC File", WowTocBundle.message("action.new.description"), TocIcon.ICON);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory,
                               CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(WowTocBundle.message("action.new.title"));
        builder.addKind(WowTocBundle.message("action.new.kind"), TocIcon.ICON, "TOC File.toc");
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, @NotNull String s, String s1) {
        return "NewTocFile";
    }
}
