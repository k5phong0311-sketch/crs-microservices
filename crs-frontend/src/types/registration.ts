export interface Registration {
  id: number;
  studentId: number;
  courseId: number;
  status: 'REGISTERED' | 'CANCELLED';
  createdAt: string; 
}

export interface RegistrationRequest {
  studentId: number;
  courseId: number;
}
