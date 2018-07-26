package com.xgt.service.rdcard;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import java.io.File;

/**
 *  读卡器接口
 * @author liuao
 * @date 2018/7/8 11:41
 */
public interface IRdcardDll extends Library {

/*
5． 如果身份证，读卡成功后，可以使用一下函数直接获得文字信息
    int GetName    (char *buf);           读取姓名
    int GetSex      (char BYTE *buf);     读取性别编码
    int GetFolk     (char BYTE *buf);     读取民族编码
    int GetSexGB   (char BYTE *buf);     读取性别
    int GetFolkGB   (char BYTE *buf);     读取民族
    int GetBirth     (char BYTE *buf);     读取出生
    int GetAddr     (char BYTE *buf);     读取住址
    int GetIDNum   (char BYTE *buf);     读取公民身份号码
    int GetDep      (char BYTE *buf);     读取签发机关
    int GetBegin    (char BYTE *buf);     读取有效期起
    int GetEnd      (char BYTE *buf);     读取有效期止
    int GetNewAddr  (char BYTE *buf);    读取最新地址
    int GetBmpPath  (char BYTE *buf);    读取头像图片路径

6． 如果外国人永久居留证，读卡成功后，可以使用一下函数直接获得文字信息
    int FID_GetEnName (char *buf);            读取英文姓名
    int FID_ GetChName (char *buf);            读取中文姓名
    int FID_ GetNationality (char *buf);          读取国籍
    int FID_GetChNationality(char *pbuf)   读取国籍中文简称
    int FID_GetSex (char BYTE *buf);       读取性别编码
    int FID_GetSexGB (char BYTE *buf);      读取性别
    int FID_GetBirth (char BYTE *buf);       读取出生
    int FID_GetIDNum (char BYTE *buf);      读取公民身份号码
    int FID_GetDep (char BYTE *buf);       读取签发机关
    int FID_GetBegin (char BYTE *buf);       读取有效期起
    int FID_GetEnd (char BYTE *buf);       读取有效期止
    int FID_ GetVersion (char BYTE *buf);      读取证件版本号
    int GetBmpPath  (char BYTE *buf);      读取头像图片路径*/

    File f = new File("");

    String directory = f.getAbsolutePath();
    String dllPath = directory + "\\RdCard";
    IRdcardDll bell = (IRdcardDll) Native.loadLibrary(dllPath, IRdcardDll.class);

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
