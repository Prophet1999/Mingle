import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8080/user';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const token = this.authService.getToken();
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    getCurrentUser(): Observable<any> {
        return this.http.get(`${this.apiUrl}/me`, { headers: this.getHeaders() });
    }

    updateProfile(user: any): Observable<any> {
        return this.http.put(`${this.apiUrl}/me`, user, { headers: this.getHeaders() });
    }
}
