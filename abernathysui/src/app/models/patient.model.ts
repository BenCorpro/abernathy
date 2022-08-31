export class Patient{
    id!: number;
    firstname!: string;
    lastname!: string;
    birthdate!: Date;
    gender!: 'MALE' | 'FEMALE';
    address!: string | null;
    phone!: string | null;
}