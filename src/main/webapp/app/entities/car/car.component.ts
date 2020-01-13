import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICar } from 'app/shared/model/car.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CarService } from './car.service';
import { CarDeleteDialogComponent } from './car-delete-dialog.component';

@Component({
  selector: 'jhi-car',
  templateUrl: './car.component.html'
})
export class CarComponent implements OnInit, OnDestroy {
  cars: ICar[];
  carsFiltered: ICar[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  brandFilter: any;
  enginePowerFilter: any;
  trunkSizeFilter: any;

  constructor(
    protected carService: CarService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.cars = [];
    this.carsFiltered = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.carService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICar[]>) => this.paginateCars(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.cars = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCars();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICar) {
    return item.id;
  }

  registerChangeInCars() {
    this.eventSubscriber = this.eventManager.subscribe('carListModification', () => this.reset());
  }

  delete(car: ICar) {
    const modalRef = this.modalService.open(CarDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.car = car;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCars(data: ICar[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.cars.push(data[i]);
      this.carsFiltered.push(data[i]);
    }
  }

  filterCars() {
    this.carsFiltered = [];
    let respectFiltering;
    for (let i = 0; i < this.cars.length; i++) {
      respectFiltering = true;
      if (this.brandFilter !== null && this.brandFilter !== undefined && this.brandFilter !== '') {
        if (this.cars[i].brand.toUpperCase() !== this.brandFilter.toString().toUpperCase()) {
          respectFiltering = false;
        }
      }
      if (this.enginePowerFilter !== null && this.enginePowerFilter !== undefined) {
        if (this.cars[i].enginePower !== this.enginePowerFilter) {
          respectFiltering = false;
        }
      }
      if (this.trunkSizeFilter !== null && this.trunkSizeFilter !== undefined) {
        if (this.cars[i].trunkSize !== this.trunkSizeFilter) {
          respectFiltering = false;
        }
      }

      if (respectFiltering) {
        this.carsFiltered.push(this.cars[i]);
      }
    }
  }
  resetFilter() {
    this.carsFiltered = this.cars;
    this.brandFilter = undefined;
    this.enginePowerFilter = undefined;
    this.trunkSizeFilter = undefined;
  }
}
