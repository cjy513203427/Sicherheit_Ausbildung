package com.xgt.service.rdcard;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.xgt.util.FileToolUtil;
import org.apache.commons.codec.binary.Base64;

/**
 * 锟斤拷锟斤拷锟斤拷示锟斤拷
 * 
 * @author weid
 * @date 20140218
 * 
 */
public class Person {
	
	private String sysName = System.getProperty("os.name");

	public Person(String path) {

	}

	public interface RdcardDll extends Library {
		File f = new File("");
		
		String a = f.getAbsolutePath();

		RdcardDll bell = (RdcardDll) Native.loadLibrary("RdCard", RdcardDll.class);

		//modified by jiadl 20141011
//		int UCommand1(String pCmd, IntByReference prag0, IntByReference prag1,
//				IntByReference prag2);
		int UCommand1(String pCmd, IntByReference prag0, IntByReference prag1,
                      byte[] prag2);

		int GetName(byte[] buf);

		int GetSexGB(byte[] buf);

		int GetFolkGB(byte[] buf);

		int GetAddr(byte[] buf);

		int GetIDNum(byte[] buf);

		int GetDep(byte[] buf);

		int GetBirth(byte[] buf);

		int GetBegin(byte[] buf);

		int GetEnd(byte[] buf);

		int GetBmpPath(byte[] buf);
		
		
		
		
		int FID_GetEnName(byte[] buf);
		int FID_GetChName(byte[] buf);
		int FID_GetNationality(byte[] buf);
		int FID_GetSex(byte[] buf);
		int FID_GetSexGB(byte[] buf);
		int FID_GetBirth(byte[] buf);
		int FID_GetIDNum(byte[] buf);
		int FID_GetDep(byte[] buf);
		int FID_GetBegin(byte[] buf);
		int FID_GetEnd(byte[] buf);
		int FID_GetVersion(byte[] buf);

	}

	public void open() throws IOException {
		
		System.out.println("RdCard");
		String aa;
		aa = String.format("%c", 0x41);

		IntByReference bb = new IntByReference();
		bb.setValue(0);
		IntByReference cc = new IntByReference();
		cc.setValue(8811);
		

		byte[] dd = { 0x02, 0x27, 0x00, 0x00 };    //9986

		System.out.println(RdcardDll.bell.UCommand1(aa, bb, cc, dd)); // 锟斤拷始锟斤拷

		aa = String.format("%c", 0x43);
		System.out.println(RdcardDll.bell.UCommand1(aa, bb, cc, dd));

		aa = String.format("%c", 0x49);
		
		//modified by jiadl 20170623
		//字符串后面必须加“0”
		String strPara2 = "D:\\测试\\"+"\0";
		System.out.println(System.getProperty("file.encoding"));
		byte[] bytePara2 = strPara2.getBytes("GBK");
		
		int flag = RdcardDll.bell.UCommand1(aa, bb, cc, bytePara2);
		
		System.out.println(flag);
		if((flag==62171) || (flag==62172))
		{
			Map<String,Object> result = new HashMap<>();

			// 获取姓名
			byte[] name1 = new byte[20];
			RdcardDll.bell.GetName(name1);
			String name = new String(name1, "GBK");
			System.out.println(name);
			name = reEncoding(name, "UTF-8");
			result.put("name", name);

			// 获取性别
			byte[] sex1 = new byte[20];
			RdcardDll.bell.GetSexGB(sex1);
			String sex = new String(sex1, "GBK");
			sex = reEncoding(sex, "UTF-8");
			result.put("sex", sex);

			// 民族
			byte[] min1 = new byte[20];
			RdcardDll.bell.GetFolkGB(min1);
			String nation = new String(min1, "GBK");
			nation = reEncoding(nation, "UTF-8");
			result.put("nation", nation);

			// 获取地址
			byte[] addr = new byte[50];
			RdcardDll.bell.GetAddr(addr);
			String add = new String(addr, "GBK");
			add = reEncoding(add, "UTF-8");
			result.put("address", add);

			// 获取身份证号
			byte[] idNum = new byte[50];
			RdcardDll.bell.GetIDNum(idNum);
			String idNumber = new String(idNum, "GBK");
			idNumber = reEncoding(idNumber, "UTF-8");
			result.put("idNumber", idNumber);

			// 签发机关
			byte[] dep1 = new byte[50];
			RdcardDll.bell.GetDep(dep1);
			String idIssued = new String(dep1, "GBK");
			idIssued = reEncoding(idIssued, "UTF-8");
			result.put("idIssued", idIssued);

			// 生日
			byte[] bir1 = new byte[20];
			RdcardDll.bell.GetBirth(bir1);
			String birthday = new String(bir1, "GBK");
			birthday = reEncoding(birthday, "UTF-8");
			result.put("birthday", birthday);

			// 有效开始时间
			byte[] begin = new byte[20];
			RdcardDll.bell.GetBegin(begin);
			String issuedDate = new String(begin, "GBK");
			issuedDate = reEncoding(issuedDate, "UTF-8");
			result.put("issuedDate", issuedDate);

			// 有效期截至日期
			byte[] end1 = new byte[20];
			RdcardDll.bell.GetEnd(end1);
			String validDate = new String(end1, "GBK");
			validDate = reEncoding(validDate, "UTF-8");
			result.put("validDate", validDate);

			// 头像base64
			byte[] path1 = new byte[100];
			RdcardDll.bell.GetBmpPath(path1);
			String path = new String(path1, "GBK");
			path = reEncoding(path,"UTF-8");
			FileInputStream inputStream = new FileInputStream(path);
			String picBase64 = new String(Base64.encodeBase64(
					FileToolUtil.inputStreamToByte(inputStream)));
			result.put("picBase64", picBase64);

			FileWriter writer;
			try {
				writer = new FileWriter("D://obj.txt");

				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(flag==62173)
		{
	
			byte[] name1 = new byte[120];
			RdcardDll.bell.FID_GetEnName(name1);
			String name = new String(name1, "GBK");
			name = reEncoding(name, "UTF-8");
			System.out.println(name);
	
			byte[] sex1 = new byte[20];
			RdcardDll.bell.FID_GetSexGB(sex1);
			String sex = new String(sex1, "GBK");
			sex = reEncoding(sex, "UTF-8");
			System.out.println(sex);
	
	
			byte[] addr = new byte[50];
			RdcardDll.bell.FID_GetChName(addr);
			String add = new String(addr, "GBK");
			add = reEncoding(add, "UTF-8");
			System.out.println(add);
	
			byte[] idNum = new byte[50];
			RdcardDll.bell.FID_GetIDNum(idNum);
			String id = new String(idNum, "GBK");
			id = reEncoding(id, "UTF-8");
			System.out.println("id:" + id);
	
			byte[] dep1 = new byte[20];
			RdcardDll.bell.FID_GetNationality(dep1);
			String dep = new String(dep1, "GBK");
			dep = reEncoding(dep, "UTF-8");
			System.out.println(dep);
	
			byte[] bir1 = new byte[20];
			RdcardDll.bell.FID_GetBirth(bir1);
			String bir = new String(bir1, "GBK");
			bir = reEncoding(bir, "UTF-8");
			System.out.println(bir);
	
			byte[] begin = new byte[20];
			RdcardDll.bell.FID_GetBegin(begin);
			String begi = new String(begin, "GBK");
			begi = reEncoding(begi, "UTF-8");
			System.out.println(begi);
	
			byte[] end1 = new byte[20];
			RdcardDll.bell.FID_GetEnd(end1);
			String end = new String(end1, "GBK");
			end = reEncoding(end, "UTF-8");
			System.out.println(end);
	
			byte[] path1 = new byte[50];
			RdcardDll.bell.GetBmpPath(path1);
			String path = new String(path1, "GBK");
			 path = reEncoding(path,"UTF-8");
			System.out.println("path*****" + path + "*******");
	
			FileWriter writer;
			try {
				writer = new FileWriter("D://obj.txt");
				writer.write(name + "\r\n");
				writer.write(id + "\r\n");
				writer.write(dep + "\r\n");
				writer.write(add + "\r\n");
				writer.write(sex + "\r\n");
				writer.write(bir + "\r\n");
				writer.write(begi + "\r\n");
				writer.write(end + "\r\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String reEncoding(String text, String newEncoding) {
		String rs = null;
		try {
			rs = new String(text.getBytes(), newEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static void main(String[] args) throws Exception {
		Person person = new Person("D://Download//");
		person.open();
	}

}