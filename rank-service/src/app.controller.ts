import { Controller, Get, Query } from '@nestjs/common';

@Controller()
export class AppController {
  @Get('greet')
  greet(@Query('input') input?: string): string {
    return `hello ${input || 'person'} from rank service`;
  }
}
