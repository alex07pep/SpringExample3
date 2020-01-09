import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Tema3SpringSharedModule } from 'app/shared/shared.module';
import { Tema3SpringCoreModule } from 'app/core/core.module';
import { Tema3SpringAppRoutingModule } from './app-routing.module';
import { Tema3SpringHomeModule } from './home/home.module';
import { Tema3SpringEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Tema3SpringSharedModule,
    Tema3SpringCoreModule,
    Tema3SpringHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Tema3SpringEntityModule,
    Tema3SpringAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class Tema3SpringAppModule {}
