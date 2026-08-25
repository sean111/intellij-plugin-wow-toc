package com.github.sean111.wowtoc.psi.impl;

import com.github.sean111.wowtoc.psi.TocElementFactory;
import com.github.sean111.wowtoc.psi.TocRefer;
import com.github.sean111.wowtoc.psi.TocTag;
import com.github.sean111.wowtoc.psi.TocTypes;
import com.github.sean111.wowtoc.reference.TocReference;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public class TocPsiImplUtil {
    /**
      * Gets the tag name.
     *
      * @param psiElement the PSI element
      * @return the tag name
     */
    public static String getTagName(TocTag psiElement) {
        ASTNode node = psiElement.getNode().findChildByType(TocTypes.TAG_NAME);
        if (node != null) {
            return node.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    /**
      * Gets the range containing the tag prefix and tag name.
     *
      * @param psiElement the PSI element
      * @return the content range
     */
    public static TextRange getKeyRange(TocTag psiElement) {
        ASTNode tagPrefixNode = psiElement.getNode().findChildByType(TocTypes.TAG_PREFIX);
        ASTNode tagNameNode = psiElement.getNode().findChildByType(TocTypes.TAG_NAME);
        if (tagPrefixNode == null) {
            return null;
        }
        int start = tagPrefixNode.getStartOffset();
        ASTNode node = tagNameNode == null ? tagPrefixNode : tagNameNode;
        int end = node.getTextRange().getEndOffset();
        return new TextRange(start, end);
    }

    /**
      * Gets the file name.
     *
      * @param psiElement the PSI element
      * @return the file name
     */
    public static String getFileName(TocRefer psiElement) {
        ASTNode node = psiElement.getNode().findChildByType(TocTypes.FILE_NAME);
        if (node != null) {
            return node.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getName(TocRefer psiElement) {
        return getFileName(psiElement);
    }

    public static PsiElement setName(TocRefer psiElement, String newName) {
        ASTNode node = psiElement.getNode().findChildByType(TocTypes.FILE_NAME);
        if (node != null) {
            String fileName = node.getText();
            String prefix = "";
            if (fileName.contains("\\") || fileName.contains("/")) {
                int endIndex = Math.max(fileName.lastIndexOf("\\"), fileName.lastIndexOf("/"));
                prefix = fileName.substring(0, endIndex + 1);
            }
            // Preserve the directory prefix when a referenced file is renamed.
            TocRefer refer = TocElementFactory.createRefer(psiElement.getProject(), prefix + newName);
            ASTNode newNode = refer.getFirstChild().getNode();
            psiElement.getNode().replaceChild(node, newNode);
        }
        return psiElement;
    }

    public static PsiElement getNameIdentifier(TocRefer psiElement) {
        ASTNode node = psiElement.getNode().findChildByType(TocTypes.FILE_NAME);
        if (node != null) {
            return node.getPsi();
        } else {
            return null;
        }
    }

    public static PsiReference getReference(TocRefer psiElement) {
        // Reference ranges are relative to this PSI element, not the containing file.
        return new TocReference(psiElement, new TextRange(0, psiElement.getTextLength()));
    }
}
