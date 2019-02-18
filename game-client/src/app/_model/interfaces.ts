export interface Field{
  x:number,
  y:number,
  value:FieldEnum,
}

export enum FieldEnum {
  EMPTY=0,
  X=1,
  O=2
}
