import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class MatchingService {
    private apiUrl = 'http://localhost:8080/matching';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const token = this.authService.getToken();
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    getCandidates(distance: number = 50): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/candidates?distance=${distance}`, { headers: this.getHeaders() });
    }

    likeUser(userId: number): Observable<any> {
        return this.http.post(`${this.apiUrl}/like/${userId}`, {}, { headers: this.getHeaders() });
    }

    dislikeUser(userId: number): Observable<any> {
        return this.http.post(`${this.apiUrl}/dislike/${userId}`, {}, { headers: this.getHeaders() });
    }

    getMatches(): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/matches`, { headers: this.getHeaders() });
    }

    updateLocation(lat: number, lon: number): Observable<any> {
        return this.http.post(`${this.apiUrl}/location`, { latitude: lat, longitude: lon }, { headers: this.getHeaders() });
    }
}
