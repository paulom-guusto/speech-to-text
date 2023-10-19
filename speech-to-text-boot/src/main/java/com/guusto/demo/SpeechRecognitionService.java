package com.guusto.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

@Service
public class SpeechRecognitionService {

	public String transcript(byte[] bytes) throws IOException {


		// Instantiates a client
		try (SpeechClient speechClient = SpeechClient.create()) {

			  RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder().setContent(ByteString.copyFrom(bytes)).build();
			  ArrayList<String> languageList = new ArrayList<>();
			  languageList.add("es-ES");

		      // Configure request to enable multiple languages
		      RecognitionConfig config =
		          RecognitionConfig.newBuilder()
		              .setEncoding(AudioEncoding.LINEAR16)
//		              .setSampleRateHertz(16000)
		              .setLanguageCode("en-US")
		              .addAllAlternativeLanguageCodes(languageList)
		              .build();
		      
			RecognizeResponse response = speechClient.recognize(config, recognitionAudio);
			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				return alternative.getTranscript();
			}

		}

		return null;

	}

}