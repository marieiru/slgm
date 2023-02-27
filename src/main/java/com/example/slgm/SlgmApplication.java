package com.example.slgm;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.example.slgm.AppConfig;

@SpringBootApplication
@EntityScan("com.example.slgm.entity")//

public class SlgmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlgmApplication.class, args);
	}

	
	@Bean//@Beanアノテーションを付けたメソッドを定義しておくことでSpring Frameworkがオブジェクトを登録して後で使える
	public AppConfig appConfig() {
		
		// 起動時のディレクトリをAppConfigのimageDirフィールドに保持しておく
		File imageDir = new File("images");
		imageDir = imageDir.getAbsoluteFile();
		
		// imagesフォルダがなかったら作成する
		if(!imageDir.exists()) {
			imageDir.mkdir();
		} else {
		       File file = new File(imageDir + "/picture.jpg");
		       
		        //deleteメソッドを使用してファイルを削除する
		        file.delete();		
		}
		AppConfig appConfig = new AppConfig();
		appConfig.setImageDir(imageDir);
		return appConfig;
	}		
		
	
	
	
}
