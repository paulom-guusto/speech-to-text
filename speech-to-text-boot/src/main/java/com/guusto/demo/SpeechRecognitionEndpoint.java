package com.guusto.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SpeechRecognitionEndpoint {

	@Autowired
	private SpeechRecognitionService speechRecognitionService;

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		return speechRecognitionService.transcript(file.getBytes());
	}

}
