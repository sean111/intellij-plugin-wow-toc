package com.github.sean111.wowtoc.util;

import com.github.sean111.wowtoc.constant.Constants;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.util.HashSet;
import java.util.Set;

public class TocUtil {
    /**
     * Gets all supported file names in the PSI element's directory and subdirectories.
     *
     * @param psiElement the PSI element
     * @return the file-name set
     */
    public static Set<String> getFileNames(PsiElement psiElement) {
        Set<String> result = new HashSet<>();
        PsiDirectory baseDirectory = psiElement.getContainingFile().getParent();
        if (baseDirectory != null) {
            getFileNames(baseDirectory, "", result);
        }
        return result;
    }

    /**
     * Adds supported file names from a directory and its subdirectories to the result set.
     *
     * @param baseDirectory the root directory
     * @param dirName the relative directory name currently being visited
     * @param result the result set
     */
    public static void getFileNames(PsiDirectory baseDirectory, String dirName, Set<String> result) {
        PsiFile[] psiFiles = baseDirectory.getFiles();
        for (PsiFile file : psiFiles) {
            String name = file.getName();
            if (name.matches(Constants.REGEX_FILE_NAME)) {
                result.add(dirName + name);
            }
        }
        PsiDirectory[] psiDirectories = baseDirectory.getSubdirectories();
        for (PsiDirectory directory : psiDirectories) {
            String name = directory.getName() + "\\";
            getFileNames(directory, dirName + name, result);
        }
    }

    /**
     * Gets the directory portion of a full file name.
     *
     * @param baseDirectory the root directory
     * @param fileName the full file name
     * @param createDir whether missing directories should be created
     * @return the PSI directory
     */
    public static PsiDirectory getDirectory(PsiDirectory baseDirectory, String fileName, boolean createDir) {
        PsiDirectory result = baseDirectory;
        String[] array = fileName.split("[/\\\\]");
        for (int i = 0; i < array.length - 1; i++) {
            if (result != null) {
                PsiDirectory directory = result.findSubdirectory(array[i]);
                if (directory == null && createDir) {
                    result = result.createSubdirectory(array[i]);
                } else {
                    result = directory;
                }
            }
        }
        return result;
    }

    /**
     * Gets a file name without its directory prefix.
     *
     * @param fileName the full file name
     * @return the file name without its directory prefix
     */
    public static String getFileName(String fileName) {
        String[] array = fileName.split("[/\\\\]");
        return array[array.length - 1];
    }
}
