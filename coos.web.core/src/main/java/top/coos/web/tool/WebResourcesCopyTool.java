package top.coos.web.tool;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.http.HttpServletRequest;

import top.coos.constant.Constant;
import top.coos.exception.CoreException;
import top.coos.tool.file.FileTool;
import top.coos.tool.string.StringHelper;
import top.coos.util.PathUtil;
import top.coos.web.constant.WebConstant;

public class WebResourcesCopyTool {

	private static JarFile jarFile;

	private static String getPath(String packagepath, ClassLoader classLoader) {

		try {
			String path = classLoader.getResource(packagepath).getPath();
			path = path.replace("!/" + packagepath, "").replace("file:", "");
			return path;
		} catch (Exception e) {
		}
		return null;

	}

	// public static void copy(String packagepath) throws CoreException {
	// copyByPath(packagepath);
	// }

	public static void copyByPath(HttpServletRequest request, String packagepath) throws CoreException {

		copyByPath(request, packagepath, Constant.Class.CLASS_LOADER, null);
	}

	public static void copyByPath(HttpServletRequest request, String packagepath, ClassLoader classLoader, JarFile jar)
			throws CoreException {

		// System.out.println("copyByPath:" + packagepath);
		String resourcepath = getPath(packagepath + "/resource", classLoader);
		if (!StringHelper.isEmpty(resourcepath)) {
			copy(resourcepath, packagepath + "/resource", WebConstant.Path.getWebServerResourcePath(request), jar);
		}
		String viewspath = getPath(packagepath + "/views", classLoader);

		if (!StringHelper.isEmpty(viewspath)) {
			copy(viewspath, packagepath + "/views", WebConstant.Path.getWebServerViewPath(request), jar);
		}
	}

	public static void copy(String path, String packagepath, String topath) throws CoreException {

		copy(path, packagepath, topath, null);
	}

	public static void copy(String path, String packagepath, String topath, JarFile jar) throws CoreException {

		try {
			path = PathUtil.decode(path);
			topath = PathUtil.decode(topath);
			if (jar != null || new File(path).getName().indexOf(".jar") > 0) {
				String jarPath = path;
				if (jar != null) {
					jarFile = jar;
				} else {
					jarFile = new JarFile(jarPath);
				}
				Enumeration<JarEntry> es = jarFile.entries();
				while (es.hasMoreElements()) {
					JarEntry e = es.nextElement();
					String name = e.getName();
					if (e.isDirectory()) {
					} else {
						if (name.indexOf(packagepath) == 0) {
							File pagefile = new File(topath + "/" + (name.replace(packagepath, "")));
							InputStream stream = jarFile.getInputStream(e);

							byte[] bytes = FileTool.readBytes(stream);
							if (bytes == null || bytes.length < 1) {
								continue;
							}
							FileTool.save(pagefile, bytes);
						}
					}
				}

			} else {
				FileTool.copyDir(path, topath, null);
			}
		} catch (Exception e) {
			System.out.println(path + " to " + topath);
			throw new CoreException(e);
		}
	}

}
