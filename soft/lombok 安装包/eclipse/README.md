Lombok 安装
---

使用 lombok 是需要安装的，如果不安装，IDE 则无法解析 lombok 注解。
先在官网下载最新版本的 JAR 包，现在是 0.11.2 版本，我用的是 0.11.0第一次使用的时候我下载的是最新版本的，也就是我现在用的 0.11.0，到现在已经更新了两个版本，更新的好快啊 ... ...

1 双击下载下来的 JAR 包安装 lombok 我选择这种方式安装的时候提示没有发现任何 IDE，所以我没安装成功，我是手动安装的。如果你想以这种方式安装，请参考官网的视频。

2 eclipse / myeclipse 手动安装 lombok
* 将 lombok.jar 复制到 myeclipse.ini / eclipse.ini 所在的文件夹目录下
* 打开 eclipse.ini / myeclipse.ini，在最后面插入以下两行并保存：
```
-Xbootclasspath/a:lombok.jar
-javaagent:lombok.jar  
```
* 重启 eclipse / myeclipse