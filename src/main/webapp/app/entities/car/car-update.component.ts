import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICar, Car } from 'app/shared/model/car.model';
import { CarService } from './car.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-car-update',
  templateUrl: './car-update.component.html'
})
export class CarUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    licensePlate: [null, [Validators.required]],
    brand: [null, [Validators.required]],
    model: [],
    productionYear: [null, [Validators.required]],
    engineSize: [null, [Validators.required]],
    fuel: [],
    enginePower: [],
    engineTorque: [],
    trunkSize: [],
    price: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carService: CarService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ car }) => {
      this.updateForm(car);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(car: ICar) {
    this.editForm.patchValue({
      id: car.id,
      licensePlate: car.licensePlate,
      brand: car.brand,
      model: car.model,
      productionYear: car.productionYear,
      engineSize: car.engineSize,
      fuel: car.fuel,
      enginePower: car.enginePower,
      engineTorque: car.engineTorque,
      trunkSize: car.trunkSize,
      price: car.price,
      user: car.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const car = this.createFromForm();
    if (car.id !== undefined) {
      this.subscribeToSaveResponse(this.carService.update(car));
    } else {
      this.subscribeToSaveResponse(this.carService.create(car));
    }
  }

  private createFromForm(): ICar {
    return {
      ...new Car(),
      id: this.editForm.get(['id']).value,
      licensePlate: this.editForm.get(['licensePlate']).value,
      brand: this.editForm.get(['brand']).value,
      model: this.editForm.get(['model']).value,
      productionYear: this.editForm.get(['productionYear']).value,
      engineSize: this.editForm.get(['engineSize']).value,
      fuel: this.editForm.get(['fuel']).value,
      enginePower: this.editForm.get(['enginePower']).value,
      engineTorque: this.editForm.get(['engineTorque']).value,
      trunkSize: this.editForm.get(['trunkSize']).value,
      price: this.editForm.get(['price']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICar>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
