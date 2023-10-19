import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({providedIn:'root'})
export class AppService {
	baseURL: string = "http://localhost:8080/";

	constructor(private http: HttpClient) {
	}
	
	convertMessage(file: any): Observable<any> {
		const formData = new FormData();
		formData.append('file', file, file.name);
		return this.http.post(this.baseURL, formData)
	}
}
