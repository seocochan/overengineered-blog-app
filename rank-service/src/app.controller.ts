import { Controller, Get, Query } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

@Controller()
export class AppController {
  constructor(private readonly configService: ConfigService) {}

  @Get('greet')
  greet(@Query('input') input?: string): string {
    const foo = this.configService.getOrThrow<string>('APP_FOO');
    return `${foo} ${input || 'person'} from rank service`;
  }
}
