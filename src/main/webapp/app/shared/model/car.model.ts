import { IUser } from 'app/core/user/user.model';
import { FuelType } from 'app/shared/model/enumerations/fuel-type.model';

export interface ICar {
  id?: number;
  licensePlate?: string;
  brand?: string;
  model?: string;
  productionYear?: number;
  engineSize?: number;
  fuel?: FuelType;
  enginePower?: number;
  engineTorque?: number;
  trunkSize?: number;
  price?: number;
  user?: IUser;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public licensePlate?: string,
    public brand?: string,
    public model?: string,
    public productionYear?: number,
    public engineSize?: number,
    public fuel?: FuelType,
    public enginePower?: number,
    public engineTorque?: number,
    public trunkSize?: number,
    public price?: number,
    public user?: IUser
  ) {}
}
