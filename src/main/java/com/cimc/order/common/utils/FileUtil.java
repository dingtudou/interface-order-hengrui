package com.cimc.order.common.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileUtil {

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 关闭IO
	 *
	 * @param closeables closeable
	 */
	public static void closeIo(Closeable... closeables) {
		if (closeables == null) {
            return;
        }
		try {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> readLines(File file) throws IOException {
		return FileUtils.readLines(file, getFileCharsetSimple(file));
	}

	/**
	 * 简单获取文件编码格式
	 *
	 * @param file 文件
	 * @return 文件编码
	 */
	public static String getFileCharsetSimple(File file) {
		int p = 0;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			p = (is.read() << 8) + is.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIo(is);
		}
		switch (p) {
		case 0xefbb:
			return "UTF-8";
		case 0xfffe:
			return "Unicode";
		case 0xfeff:
			return "UTF-16BE";
		default:
			return "GBK";
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public static String getsuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 文件类型
	public static String getContentType(File file) {
		String contentType = null;
		try {
			contentType = new MimetypesFileTypeMap().getContentType(file);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getContentType:" + e);
		}
		return contentType;
	}

	// 文件名称
	public static String getFileName(File file) {
		String fieName = null;
		try {
			fieName = file.getName();
		} catch (Exception e) {
			log.error("getFileName:" + e);
		}
		return fieName;
	}

	// 文件大小 unit KB MB GB
	public static Long getFileSize(File file, String unit) {
		Long fieSize = 0L;
		try {
			fieSize = file.length();
		} catch (Exception e) {
			log.error("getFileName:" + e);
		}
		Integer unitCode = ("KB".equals(unit)) ? 1024 : ("MB".equals(unit) ? 1024 * 1024 : 1024 * 1024 * 1024);
		return fieSize / unitCode;
	}

	/**
	 * @param fileName
	 * @throws ParseException
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String getFileContent(String fileName) throws ParseException, IOException {
		String str = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = fis.read(buff)) != -1) {
				bos.write(buff, 0, len);
			}
			// 得到图片的字节数组
			byte[] result = bos.toByteArray();
			// 字节数组转成十六进制
			str = byte2HexStr(result);
		} catch (IOException e) {
			log.error("getFileContent:" + e);
		}
		return str;
	}

	/**
	 ** 实现字节数组向十六进制的转换方法一
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
		}
		return hs.toUpperCase();
	}

	/**
	 ** 实现字节数组向十六进制的转换的方法二
	 */
	public static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 ** 实现字节数组向十六进制的转换的方法二
	 */
	public static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath 远程文件路径
	 * @param localFilePath  本地文件路径（带文件名）
	 */
	public static boolean downloadFile(String remoteFilePath, String localFilePath) {
		boolean flag = false;
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
			flag = true;
		} catch (Exception e) {
			log.error("downloadFile:" + e);
		}
		return flag;
	}

	/**
	 ** 文件下载 基础信息bean
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public static Map<String, Object> getFileBean(String fileUrl) {
		Map<String, Object> map = Maps.newHashMap();
		try {

			File file = new File(fileUrl);
			String fileName = getFileName(file);
			String localFilePath = System.getProperty("java.io.tmpdir") + "/" + fileName;
			if (downloadFile(fileUrl, localFilePath)) {
				file = new File(localFilePath);
				map = ImmutableMap.of("contentType", getContentType(file), "size", getFileSize(file, "MB"),
						"originalFilename", fileName, "name", fileName, "content", getFileContent(localFilePath));
			}
		} catch (Exception e) {
			log.error("getFileBean:" + e);
		}
		return map;
	}

}
