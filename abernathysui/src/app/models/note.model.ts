export class Note{
    id!: string | null;
    createdAt!: Date | null;
    updatedAt!:Date | null;
    patientId!: number;
    patientName!: string;
    recommendation!: string;
}